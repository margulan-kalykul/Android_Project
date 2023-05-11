from rest_framework import serializers
from .models import Category, Product, Commentary, Order, Rating

from django.contrib.auth.models import User


class ProductSerializer(serializers.ModelSerializer):

    class Meta:
        model = Product
        fields = '__all__'
        read_only_fields = ('id',)


class RatingSerializer(serializers.ModelSerializer):

    class Meta:
        model = Rating
        fields = '__all__'
        read_only_fields = ('id', 'product', )

class CategorySerializer(serializers.ModelSerializer):

    class Meta:
        model = Category
        fields = '__all__'

class UserField(serializers.Field):
    def to_internal_value(self, data):
        return User.objects.get(username=data)

    def to_representation(self, value):
        return value.username
class CommentSerializer(serializers.ModelSerializer):
    user = UserField()

    class Meta:
        model = Commentary
        fields = ('id', 'user', 'text', 'created_at', 'product')
        read_only_fields = ('id', 'created_at', 'product')

class OrderSerializer(serializers.ModelSerializer):

    class Meta:
        model = Order
        fields = ('products',)




class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)

    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'password')

    def create(self, validated_data):
        user = User.objects.create_user(
            username=validated_data['username'],
            email=validated_data['email'],
            password=validated_data['password']
        )
        return user