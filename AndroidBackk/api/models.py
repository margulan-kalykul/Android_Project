from django.db import models
from django.contrib.auth.models import User
from django.db.models import Avg


# Create your models here.

class Category(models.Model):
    name = models.CharField(max_length=255)

    def __str__(self):
        return self.name


class Product(models.Model):
    name = models.CharField(max_length=150)
    description = models.TextField()
    price = models.FloatField()
    image = models.TextField()
    category = models.ManyToManyField(Category, related_name='products')


    # def avg_rating(self) -> float:
    #     return Rating.objects.filter(product=self).aggregate(Avg("rating"))["rating__avg"] or 0

    def __str__(self):
        return f'{self.name}'


# class Rating(models.Model):
#     user = models.ForeignKey(User, on_delete=models.CASCADE)
#     product = models.ForeignKey(Product, on_delete=models.CASCADE, related_name='rating')
#     rating = models.IntegerField(default=0)
#
#     def __str__(self):
#         return f'{self.product.name}: {self.rating}'

