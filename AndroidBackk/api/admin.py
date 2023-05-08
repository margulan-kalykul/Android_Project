from django.contrib import admin
from .models import Category, Product


# Register your models here.
@admin.register(Product)
class VacancyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name')


@admin.register(Category)
class CompanyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name')