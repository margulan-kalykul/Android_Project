from django.contrib import admin
from .models import Category, Product, Commentary


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
