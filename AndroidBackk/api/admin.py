from django.contrib import admin
from .models import Category, Product, Commentary, Order, Rating


# Register your models here.
@admin.register(Product)
class VacancyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name')


@admin.register(Category)
class CompanyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name')

@admin.register(Commentary)
class CommentaryAdmin(admin.ModelAdmin):
    list_display = ('product', 'user', 'text')

@admin.register(Order)
class OrderAdmin(admin.ModelAdmin):
    list_display = ('user',)


@admin.register(Rating)
class RatingAdmin(admin.ModelAdmin):
    list_display = ('user', 'product', 'rating')
