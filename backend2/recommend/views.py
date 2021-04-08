from django.shortcuts import render

from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from django.http import HttpResponse
from django.http import JsonResponse

# algorithm
from .models import Commercial
import pandas as pd
import json
import scipy as sp
import numpy as np
from sklearn import preprocessing
from sklearn.preprocessing import MinMaxScaler
from sklearn.cluster import KMeans
# from fcmeans import FCM

def index(request):
    return "aa"

# @api_view(['GET'])
# def func(request):
#     return Response(serializer.data, status=status.HTTP_200_OK)

def commercial(request):
    
    # if request.method == 'POST':
    #     pass

    result_commercial = algorithm()
    
    # code: 12314,
    # name: '교대역_1',
    # score: 55,
    # x: '201023', //상권영역.csv
    # y: '443482',
    return JsonResponse(result_commercial.to_dict(orient='index'))

    
def algorithm(clustering_select='K'):

    commercial_dataset = pd.DataFrame(list(Commercial.objects.all().values()))
    
    print(commercial_dataset.head())

    # sigungu_list, service_name
    sigungu_list = ['노원구', '동작구']
    service_name = '한식음식점'

    # Clustering target columns
    clust_columns = ['week', 'weekend', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun', \
                    'time_00_06', 'time_06_11', 'time_11_14', 'time_14_17', 'time_17_21', 'time_21_24', \
                 'Man', 'Woman', 'age_10', 'age_20', 'age_30', 'age_40', 'age_50', 'age_60']

    top_rate_commercial = commercial_dataset[commercial_dataset['sales_per_store'] > 50000000]
    target_dataset = top_rate_commercial.groupby(top_rate_commercial['commercial_code']).mean()[clust_columns]
    
    # Scaling target dataset
    min_max_scaler = preprocessing.MinMaxScaler()
    scaled_dataset = min_max_scaler.fit_transform(target_dataset)
    scaled_dataset = pd.DataFrame(scaled_dataset, columns=clust_columns)

    # K-means Clustering
    if clustering_select == 'K':
      kmeans = KMeans(n_clusters=10)
      kmeans.fit(scaled_dataset)
      result = scaled_dataset.copy()
      centroids = kmeans.cluster_centers_
      result["cluster"] = kmeans.labels_
      result.index=target_dataset.index

    # Fuzzy C-means Clustering
    # if clustering_select == 'C':
    #   fcm = FCM(n_clusters=10)
    #   fcm.fit(np.array(scaled_dataset))
    #   fcm_labels = fcm.predict(np.array(scaled_dataset))
    #   result = scaled_dataset.copy()
    #   result["cluster"] = fcm_labels
    #   result.index=target_dataset.index
    #   centroids = fcm.centers

    service_name_in_commercial = list(top_rate_commercial['service_name'] == service_name)    
    top_rate_commercial = top_rate_commercial.assign(service_boolean = service_name_in_commercial)

    result['service_boolean'] = top_rate_commercial.groupby('commercial_code')['service_boolean'].sum()
    result_score = result.groupby('cluster').sum()['service_boolean'] / result['cluster'].value_counts()
    result_score = list(result_score)
    result_centroid = result_score.index(max(result_score))
    
    selected_commercial = commercial_dataset[commercial_dataset['sigungu_name'].isin(sigungu_list)]
    selected_commercial = selected_commercial.groupby('commercial_code').mean()[clust_columns]
    scaled_selected_commercial = min_max_scaler.fit_transform(selected_commercial)
    scaled_selected_commercial = pd.DataFrame(scaled_selected_commercial, index=selected_commercial.index, columns=clust_columns)
    result_commercial = (pd.DataFrame(sp.spatial.distance.cdist(scaled_selected_commercial, \
                                        centroids[result_centroid].reshape(1,23), \
                                        "euclidean"), \
                index=scaled_selected_commercial.index, \
                columns=['distance'])).sort_values(by='distance').head(5)
    
    result_commercial = result_commercial.rename_axis('commercial_code').reset_index()

    result_commercial = pd.merge(
        result_commercial, commercial_dataset[['commercial_code', 'commercial_name', 'x', 'y']], \
        left_on="commercial_code", right_on="commercial_code"
    ).drop_duplicates()

    max_distance = max(sp.spatial.distance.cdist(min_max_scaler.fit_transform(commercial_dataset[clust_columns]), \
                                  centroids[result_centroid].reshape(1,23), "euclidean").reshape(8868))
    
    result_commercial['score'] = round((max_distance - result_commercial['distance'])*100 / max_distance, 2)
    result_commercial.index = range(1,6)

    return result_commercial



