# Generated by Django 2.2.3 on 2019-08-31 00:27

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0005_auto_20190831_0806'),
    ]

    operations = [
        migrations.RenameField(
            model_name='status',
            old_name='endtime',
            new_name='time',
        ),
        migrations.RemoveField(
            model_name='home',
            name='isOn',
        ),
        migrations.RemoveField(
            model_name='status',
            name='starttime',
        ),
        migrations.AddField(
            model_name='home',
            name='sensor_endtime',
            field=models.DateTimeField(default=datetime.datetime.now),
        ),
        migrations.AddField(
            model_name='home',
            name='sensor_starttime',
            field=models.DateTimeField(default=datetime.datetime.now),
        ),
    ]