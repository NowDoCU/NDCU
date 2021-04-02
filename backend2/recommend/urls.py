from django.urls import path
from . import views

app_name = 'recommend'

urlpatterns = [
    path('index/', views.index, name='index'),
    path('commercial/', views.commercial, name='commercial'),
]

