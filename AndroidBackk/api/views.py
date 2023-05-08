from django.shortcuts import render
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import *
from .serializers import ProductSerializer, CategorySerializer
from django.http import JsonResponse
# Create your views here.

@api_view(['GET', 'POST'])
def list_of_products(request):
    if request.method == 'GET':
        product = Product.objects.all()
        serializer = ProductSerializer(product, many=True)
        return Response(serializer.data)