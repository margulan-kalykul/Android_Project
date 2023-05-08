from django.urls import path, include
from api.views import *
urlpatterns = [
    path('products/', list_of_products),
    path('categories/', list_of_categories),
    path('product/<int:id>/commentaries/', comments_by_product),
    path('category/<int:id>/products/', productsByCategory),
    path('cart/<int:id>/', list_of_orders_by_user)
]