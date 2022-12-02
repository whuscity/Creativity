from glob import glob
from lxml import etree
import gc
import gzip
from dbutils.pooled_db import PooledDB
import pymysql
import threading
import logging
import time
import pandas as pd
import numpy as np
import json

dbconfig = json.load(open('./db.config'))

local_obj = threading.local()

sql_document = """
    UPDATE documents
    SET cite_count = %s
    WHERE document_id = %s
"""

class Base:
 
    def __init__(self):
        self.pool = self.create_pool()
 
    def create_pool(self):
        """
        创建数据库连接池
        :return: 连接池
        """
        pool = PooledDB(creator=pymysql,
                        maxconnections=16,  # 连接池允许的最大连接数，0和None表示不限制连接数
                        mincached=0,  # 初始化时，链接池中至少创建的空闲的链接，0表示不创建
                        maxcached=None,  # 链接池中最多闲置的链接，0和None不限制
                        maxusage=None,  # 一个链接最多被重复使用的次数，None表示无限制
                        blocking=True,  # 连接池中如果没有可用连接后，是否阻塞等待。True，等待；False，不等待然后报错
                        host='127.0.0.1',  # 此处必须是是127.0.0.1
                        port=3306,
                        user=db_config.get('user'),
                        passwd=db_config.get('password'),
                        db=db_config.get('db'),
                        charset='utf8mb4')
        return pool
 
    def update_mysql(self, sql, arg):
        """
        更新数据库
        :param sql: 执行sql语句 str
        :param arg: 添加的sql语句的参数 list[]
        """
        id = None
        try:
            db = self.pool.connection()  # 连接数据池
            cursor = db.cursor()  # 获取游标
            cursor.execute(sql,arg)
            db.commit()
        except:
            logging.exception("error in update_mysql")
            db.rollback()
        finally:
            cursor.close()
            db.close()
            return id

    def update_mysql_many(self, sql, args):
        """
        更新数据库
        :param sql: 执行sql语句 str
        :param arg: 添加的sql语句的参数 list[tuple()]
        """
        try:
            db = self.pool.connection()  # 连接数据池
            cursor = db.cursor()  # 获取游标
            cursor.executemany(sql,args)
            db.commit()
        except:
            logging.exception("error in save_mysql_many")
            db.rollback()
        finally:
            cursor.close()
            db.close()


    def update_data(self, data):
        for i in range(int(len(data)/1000)+1):
            self.update_mysql_many(sql_document,data[i*1000:(i+1)*1000])
    
    def multi_worker(self, data, workers):
        threads = []
        worker_len = int(len(data)/workers) + 1
        for i in range(workers):
            threads.append(threading.Thread(target=self.update_data,args=[data[worker_len*i:worker_len*(i+1)]],daemon=False))
        print(threads)
        for thread in threads:
            thread.start()
        
        while True:
            for i in range(len(threads)-1,-1,-1):
                if threads[i].is_alive():
                    time.sleep(60)
                else: 
                    print(threads[i].name + " ends at " + time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())))
                    threads.pop(i)
                if len(threads) == 0:
                    break


if __name__ == '__main__':
    data = pd.read_csv('/home/mariozzj/data/nih_citecount.csv')
    data = data.sort_values(by='referenced')
    data.referenced = data.referenced + 100000000
    data = data.iloc[:25395160,[1,0]].values.tolist()
    b = Base()
    sem=threading.Semaphore(16)
    b.multi_worker(data, 16)