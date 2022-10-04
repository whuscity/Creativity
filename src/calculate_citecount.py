#!/usr/bin/env python
# coding: utf-8
# run on spark machine

import findspark
from pyspark.sql import functions as F
from pyspark.sql import SparkSession
from pyspark.sql.types import *
from pyspark.sql.functions import *

findspark.init('/opt/spark-3.1.2')

spark = SparkSession \
    .builder \
    .master("spark://c8mao:7077")\
    .config("spark.executor.cores","4")\
    .config("spark.driver.memory", "20g") \
    .config("spark.executor.memory", "16g") \
    .config("spark.dynamicAllocation.enabled", "true") \
    .config("spark.dynamicAllocation.shuffleTracking.enabled", "true") \
    .config("spark.shuffle.service.enabled", "true") \
    .config("spark.hadoop.fs.permissions.umask-mode", "000") \
    .config("spark.local.dir","/home/mariozzj/tmp/spark") \
    .config("spark.ui.port","4088") \
    .appName("MarioZZJ_NIH") \
    .getOrCreate()
    # 可设置 conf 可查看：https://spark.apache.org/docs/latest/configuration.html

class SparkInit():
  def getFullpath(self, streamName):
    path = r'/home/mariozzj/data/' + self.streams[streamName][0]
    return path

  def getHeader(self, streamName):
    return self.streams[streamName][1]

  datatypedict = {
    'bool' : BooleanType(),
    'int' : IntegerType(),
    'uint' : IntegerType(),
    'long' : LongType(),
    'ulong' : LongType(),
    'float' : FloatType(),
    'string' : StringType(),
    'DateTime' : DateType(),
  }

  def getSchema(self, streamName):
    schema = StructType()
    for field in self.streams[streamName][1]:
      fieldname, fieldtype = field.split(':')
      nullable = fieldtype.endswith('?')
      if nullable:
        fieldtype = fieldtype[:-1]
      schema.add(StructField(fieldname, self.datatypedict[fieldtype], nullable))
    return schema

  def getDataframe(self, streamName):
    return spark.read.format('csv').options(header='true', delimiter=',').schema(self.getSchema(streamName)).load(self.getFullpath(streamName))

  streams = {
      'Citations' : ('open_citation_collection.csv',['citing:long', 'referenced:long'])
  }

sp = SparkInit()

Citations = sp.getDataframe('Citations')

Citecount = Citations.groupBy(Citations.referenced).agg(F.countDistinct(Citations.citing).alias("CiteCount"))

Citecount.toPandas().to_csv("/home/mariozzj/data/nih_citecount.csv",header=True,index=False)