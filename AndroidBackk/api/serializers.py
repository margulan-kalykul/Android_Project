from rest_framework import serializers
from .models import Category, Product, Commentary
class ProductSerializer(serializers.ModelSerializer):

    class Meta:
        model = Product
        fields = '__all__'

class CategorySerializer(serializers.ModelSerializer):

    class Meta:
        model = Category
        fields = '__all__'

class CommentSerializer(serializers.ModelSerializer):
    user = serializers.StringRelatedField()

    class Meta:
        model = Commentary
        fields = ('user', 'text', 'created_at', 'product')
        read_only_field = ('created_at')