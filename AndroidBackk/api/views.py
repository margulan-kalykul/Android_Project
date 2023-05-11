from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view, APIView
from rest_framework.response import Response
from rest_framework import generics
from .models import *
from .serializers import ProductSerializer, CategorySerializer, CommentSerializer, OrderSerializer, UserSerializer, RatingSerializer
from django.http import JsonResponse

from django.views.generic import View
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

@api_view(['GET', 'PUT'])
def product_ratings(request, productId):
    try:
        product = Product.objects.get(id=productId)
    except Product.DoesNotExist as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

    if request.method == 'GET':
        ratings = product.rating.all()
        serializer = RatingSerializer(ratings, many=True)
        return Response(serializer.data)

    try:
        if request.method == 'PUT':
            print(request.data.get('user'))
            ratings = Rating.objects.get(user__id=request.data.get('user'), product__id=productId)
            serializer = RatingSerializer(instance=ratings, data=request.data)
            if serializer.is_valid():  # only when data ?= data. in the create method we are providing only data
                serializer.save(product=product)
                return Response(serializer.data)
    except Rating.DoesNotExist as e:
        serializer = RatingSerializer(data=request.data)
        if serializer.is_valid():  # only when data ?= data. in the create method we are providing only data
            serializer.save(product=product)
            return Response(serializer.data)
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    # if request.method == 'PUT':
    #     print(request.data.get('user'))
    #     ratings = Rating.objects.get(user__id=request.data.get('user'), product__id=productId)
    #     serializer = RatingSerializer(instance=ratings, data=request.data)
    #     if serializer.is_valid():  # only when data ?= data. in the create method we are providing only data
    #         serializer.save(product=product)
    #         return Response(serializer.data)


# @api_view(['GET', 'PUT'])
# def change_rating_for_user(request, productId, userId):
#     try:
#         product = Product.objects.get(id=productId)
#     except Product.DoesNotExist as e:
#         return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
#
#     if request.method == 'GET':
#         ratings = Rating.objects.filter(user__id=userId, product__id=productId)
#         serializer = RatingSerializer(ratings, many=True)
#         return Response(serializer.data)
#
#     if request.method == 'PUT':
#         ratings = Rating.objects.filter(user__id=userId, product__id=productId)
#         serializer = RatingSerializer(instance=ratings, data=request.data)
#         print(request.data)
#         if serializer.is_valid():  # only when data ?= data. in the create method we are providing only data
#             serializer.save(product=product)
#             return Response(serializer.data)


@api_view(['GET', 'POST', 'DELETE'])
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
def get_comment_of_a_user(request, productId, userId):
    try:
        product = Product.objects.get(id=productId)
    except:
        return Response({'error': 'Product not Found'}, status=status.HTTP_400_BAD_REQUEST)
    if request.method == 'GET':
        # comments = product.comments.all()
        comments = Commentary.objects.filter(user__id=userId, product__id=productId)
        serializer = CommentSerializer(comments, many=True)
        return Response(serializer.data)
@api_view(['DELETE'])
def delete_comment_of_a_user(request, commentId):
    if request.method == 'DELETE':
        comment = Commentary.objects.get(id=commentId)
        comment.delete()
        return Response({"delete": "success"})


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
@api_view(['DELETE'])
def delete_product_from_order(request, productId, userId):
    try:
        user = User.objects.get(id=userId)
    except User.DoesNotExist as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

    if request.method == 'DELETE':
        asds = Order.objects.filter(user__id=userId, products__id=productId)
        asds.delete()
        return Response({"delete": "success"})


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

@api_view(['GET'])
def find_email_by_username(request, username):
    try:
        user = User.objects.get(username=username)
    except User.DoesNotExist:
        return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        data = {'email': user.email}
        return JsonResponse(data, safe=False, status=status.HTTP_200_OK)

@api_view(['GET'])
def product_by_id(request, id):
    try:
        product = Product.objects.get(id=id)
    except Product.DoesNotExist as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    if request.method == 'GET':
        serializer = ProductSerializer(product)
        return Response(serializer.data, status=status.HTTP_200_OK)


class ProductSearchView(View):
    def get(self, request):
        query = request.GET.get('q')
        if not query:
            return JsonResponse({'error': 'Please provide a query parameter'})

        products = Product.objects.filter(name__icontains=query).values()

        # Do any additional processing here

        return JsonResponse({'results': list(products)})
