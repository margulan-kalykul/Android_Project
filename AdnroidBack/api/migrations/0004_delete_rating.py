# Generated by Django 4.2.1 on 2023-05-08 10:09

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0003_alter_rating_product'),
    ]

    operations = [
        migrations.DeleteModel(
            name='Rating',
        ),
    ]
