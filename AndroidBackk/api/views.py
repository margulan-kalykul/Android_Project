from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view, APIView
from rest_framework.response import Response
from rest_framework import generics
from .models import *
from .serializers import ProductSerializer, CategorySerializer, CommentSerializer, OrderSerializer, UserSerializer
from django.http import JsonResponse


# Create your views here.

@api_view(['GET', 'POST'])
def list_of_products(request):
    if request.method == 'GET':
        product = Product.objects.all()
        serializer = ProductSerializer(product, many=True)
        return Response(serializer.data)
    if request.method == 'POST':
        serializer = ProductSerializer(data=request.data)
        if serializer.is_valid():  # only when data ?= data. in the create method we are providing only data
            serializer.save()
            return Response(serializer.data)


@api_view(['GET'])
def list_of_categories(request):
    if request.method == 'GET':
        categories = Category.objects.all()
        serializer = CategorySerializer(categories, many=True)
        return Response(serializer.data)


@api_view(['GET', 'POST'])
def comments_by_product(request, id):
    try:
        product = Product.objects.get(id=id)
    except:
        return Response({'error': 'Product not Found'}, status=status.HTTP_400_BAD_REQUEST)
    if request.method == 'GET':
        comments = product.comments.all()
        serializer = CommentSerializer(comments, many=True)
        return Response(serializer.data)
    if request.method == 'POST':
        print(request.data)
        serializer = CommentSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(product=product)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response({"error": "Error posting comment"}, status=status.HTTP_406_NOT_ACCEPTABLE)


@api_view(['GET'])
def productsByCategory(request, id):
    try:
        category = Category.objects.get(id=id)
    except Category.DoesNotExist as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    if request.method == 'GET':
        products = category.products.all()
        serializer = ProductSerializer(products, many=True)
        return Response(serializer.data)


@api_view(['GET', 'POST'])
def list_of_orders_by_user(request, id):
    try:
        user = User.objects.get(id=id)
    except User.DoesNotExist as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    if request.method == 'GET':
        orders = user.orders.all()
        serializer = OrderSerializer(orders, many=True)
        return Response(serializer.data)
    if request.method == 'POST':
        serializer = OrderSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(user=user)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response({"error": "Error posting order"}, status=status.HTTP_406_NOT_ACCEPTABLE)


class UserCreateView(generics.CreateAPIView):
    serializer_class = UserSerializer


@api_view(['GET'])
def find_user_by_username(request, username):
    try:
        user = User.objects.get(username=username)
    except User.DoesNotExist:
        return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        data = {'id': user.id}
        return JsonResponse(data, safe=False, status=status.HTTP_200_OK)
