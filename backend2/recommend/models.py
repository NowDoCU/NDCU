from django.db import models

# Create your models here.
class Commercial(models.Model):
    
    commercial_code = models.IntegerField()
    division_code = models.TextField()
    division_name = models.TextField()
    commercial_name = models.TextField()
    service_code = models.TextField()
    service_name = models.TextField()
    sales_per_store = models.IntegerField()
    x = models.IntegerField()
    y = models.IntegerField()
    sigungu_code = models.IntegerField()
    sigungu_name = models.TextField()
    dong_code = models.IntegerField()

    week = models.IntegerField()
    weekend = models.IntegerField()
    Mon = models.IntegerField()
    Tue = models.IntegerField()
    Wed = models.IntegerField()
    Thu = models.IntegerField()
    Fri = models.IntegerField()
    Sat = models.IntegerField()
    Sun = models.IntegerField()
    time_00_06 = models.IntegerField()
    time_06_11 = models.IntegerField()
    time_11_14 = models.IntegerField()
    time_14_17 = models.IntegerField()
    time_17_21 = models.IntegerField()
    time_21_24 = models.IntegerField()
    Man = models.IntegerField()
    Woman = models.IntegerField()
    age_10 = models.IntegerField()
    age_20 = models.IntegerField()
    age_30 = models.IntegerField()
    age_40 = models.IntegerField()
    age_50 = models.IntegerField()
    age_60 = models.IntegerField()

# class :
    # category
    # districts