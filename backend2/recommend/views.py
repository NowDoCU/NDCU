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
    algorithm()
    # code: 12314,
    # name: '교대역_1',
    # score: 55,
    # x: '201023', //상권영역.csv
    # y: '443482',
    return JsonResponse({'foo': 'bar'})

    
def algorithm():

    commercial_dataset = pd.DataFrame(list(Commercial.objects.all().values()))
    
    print(commercial_dataset.head())


    # # tar_clustering = my_data.groupby(my_data['상권_코드_명']).mean()[tar_attr]
    
    # # Clustring target dataset
    # target_dataset = commercial_dataset
    # target_columns = list(target_dataset.columns)
    
    # # Scaling target dataset
    # min_max_scaler = preprocessing.MinMaxScaler()
    # scaled_dataset = min_max_scaler.fit_transform(target_dataset)
    # scaled_dataset = pd.DataFrame(scaled_dataset, columns=target_columns)
    
    # # Clustering
    # kmeans = KMeans(n_clusters=10)
    # kmeans.fit(scaled_dataset)

    # filtered_data['code'] = list(filtered_sg) # commercial code

    # 결과 확인
    # result = x_scaled.copy()
    # rc = kmeans.cluster_centers_
    # result["cluster"] = kmeans.labels_
    # result.head(1)
    # final_centeroid = rc[0]
    # tmp = np.array(filtered_data[tar_attr][filtered_data[tar_attr].index == 0])

    # result_point = sp.spatial.distance.cdist(tmp, rc, "euclidean")
    
    # result_point

    return