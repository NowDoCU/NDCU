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


def index(request):
    return "aa"

# @api_view(['GET'])
# def func(request):
#     return Response(serializer.data, status=status.HTTP_200_OK)

def commercial(request):
    
    result_commercial = algorithm()

    # code: 12314,
    # name: '교대역_1',
    # score: 55,
    # x: '201023', //상권영역.csv
    # y: '443482',
    return JsonResponse({'foo': result_commercial.index})

    
def algorithm():

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
    target_columns = list(commercial_dataset.columns)

    # Scaling target dataset
    min_max_scaler = preprocessing.MinMaxScaler()
    scaled_dataset = min_max_scaler.fit_transform(target_dataset)
    scaled_dataset = pd.DataFrame(scaled_dataset, columns=clust_columns)

    # Clustering
    kmeans = KMeans(n_clusters=10)
    kmeans.fit(scaled_dataset)
    
    # Result
    result = scaled_dataset.copy()
    centroids = kmeans.cluster_centers_
    result["cluster"] = kmeans.labels_
    result.index=target_dataset.index
    result.head(1)
    
    top_rate_commercial['service_boolean'] = top_rate_commercial['service_name'] == service_name
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
    
    return result_commercial