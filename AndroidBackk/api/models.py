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
    category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='products')
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name='products', null=True)

    # def avg_rating(self) -> float:
    #     return Rating.objects.filter(product=self).aggregate(Avg("rating"))["rating__avg"] or 0

    def __str__(self):
        return f'{self.name}'


class Rating(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE, related_name='rating')
    rating = models.FloatField(default=0)

    def __str__(self):
        return f'{self.product.name}: {self.rating}'

class Commentary(models.Model):
    product = models.ForeignKey(Product, on_delete=models.CASCADE, related_name='comments')
    user = models.ForeignKey(User, on_delete=models.CASCADE, null=True, blank=True)
    text = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ['-created_at']

    def __str__(self):
        return "Comment {} by {}".format(self.created_at, self.user)

class Order(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name='orders')
    products = models.ForeignKey(Product, related_name="orders", on_delete=models.CASCADE)


    def __str__(self):
        return self.user.__str__()

