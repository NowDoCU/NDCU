from django.db import models

# Create your models here.
class Commercial(models.Model):
    
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