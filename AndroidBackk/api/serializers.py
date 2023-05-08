from rest_framework import serializers
from .models import Category, Product, Commentary

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
        fields = ('user', 'text', 'created_at', 'product')
        read_only_fields = ('created_at', )