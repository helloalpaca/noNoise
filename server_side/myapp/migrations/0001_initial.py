# Generated by Django 2.2.3 on 2019-08-30 09:02

import datetime
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Home',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('home_No', models.IntegerField(default=0)),
                ('message', models.TextField(default='특별한 일정이 없습니다.')),
                ('isOn', models.IntegerField(default=1)),
            ],
            options={
                'db_table': 'Home',
            },
        ),
        migrations.CreateModel(
            name='Notice',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('time', models.DateTimeField(default=datetime.datetime.now)),
                ('contents', models.TextField(default='특별한 공지사항은 없습니다.')),
            ],
            options={
                'db_table': 'Notice',
            },
        ),
        migrations.CreateModel(
            name='Status',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('noise', models.FloatField(default=0)),
                ('vibration', models.FloatField(default=0)),
                ('time', models.DateTimeField(default=datetime.datetime.now)),
                ('home_No', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.Home')),
            ],
            options={
                'db_table': 'Status',
            },
        ),
    ]