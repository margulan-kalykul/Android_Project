from django.urls import path, include
from api.views import *

from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

urlpatterns = [
    path('products/', list_of_products),
    path('product/<int:id>/commentaries/', comments_by_product),

    path('categories/', list_of_categories),
    path('category/<int:id>/products/', productsByCategory),

    path('cart/<int:id>/', list_of_orders_by_user),

    path('register/user/', UserCreateView.as_view()),
    path('token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
]