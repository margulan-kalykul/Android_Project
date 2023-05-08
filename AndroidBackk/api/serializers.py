from rest_framework import serializers
from .models import Category, Product, Commentary, Order

from django.contrib.auth.models import User


class ProductSerializer(serializers.ModelSerializer):

    class Meta:
        model = Product
        fields = '__all__'

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
        read_only_fields = ('id', 'created_at')

class OrderSerializer(serializers.ModelSerializer):
    user = UserField()

    class Meta:
        model = Order
        fields = ('id', 'user', 'products')
        read_only_fields = ('id', 'user')


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