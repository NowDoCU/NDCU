from django.contrib import admin
from .models import Commercial, StoreRentalPrice, OfficeWorker

# Register your models here.
admin.site.register(Commercial)
admin.site.register(StoreRentalPrice)
admin.site.register(OfficeWorker)
