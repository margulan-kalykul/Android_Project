from django.urls import path, include
from api.views import *
urlpatterns = [
    path('products/', list_of_products)
]