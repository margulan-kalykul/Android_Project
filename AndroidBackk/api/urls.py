from django.urls import path, include
from api.views import *
urlpatterns = [
    path('products/', list_of_products),
    path('categories/', list_of_categories),
    path('product/<int:id>/commentaries/', comments_by_product),
    path('category/<int:id>/products/', productsByCategory)
]