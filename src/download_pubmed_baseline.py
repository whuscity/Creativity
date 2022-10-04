#!/usr/bin/env python
# coding: utf-8

# In[23]:
import os
from contextlib import closing
import threading
import requests
import json as js
import pandas as pd

# In[25]:
def download(url, file_name):
    if os.path.isfile(os.path.join(out_dir, file_name)):
        return    ####如果之前下载过这个文件，就跳过
    with closing(requests.get(url, stream=True, headers=headers, timeout=timeout)) as r:
        rc = r.status_code
        if 299 < rc or rc < 200:
            print ('returnCode%s\t%s' % (rc, url))
            return
        content_length = int(r.headers.get('content-length', '0'))
        if content_length == 0:
            print ('size0\t%s' % url)
            return
        try:
            with open(os.path.join(out_dir, file_name), 'wb') as f:
                for data in r.iter_content(1024):
                    f.write(data)
        except:
            print('savefail\t%s' % url)


# In[26]:

def loop(fs):
    print ('thread %s is running...' % threading.current_thread().name)
    while True:
        try:
            with lock:
                url,file_name = next(fs)
                print(file_name)
        except StopIteration:
            break
        try:
            download(url, file_name)
        except:
            print ('exceptfail\t%s' % url)
    print ('thread %s is end...' % threading.current_thread().name)

# In[29]:

def generate():
    for name in name_list:
        fetch = []
        fetch.append("https://ftp.ncbi.nlm.nih.gov/pubmed/baseline/"+name)
        fetch.append(name)
        try:
            yield fetch
        except:
            break

# In[32]:

list_path="./urls.csv" #url 文件夹
out_dir = "/home/mariozzj/data/pubmed/" #输出文件夹
headers = {
'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36'
}
thread_num = 16 #线程数
timeout = 30 #http请求超时设置
lock = threading.Lock()
if not os.path.exists(out_dir):
    os.makedirs(out_dir)
urls = pd.read_csv(list_path,header=None)
name_list = urls.values.ravel().tolist()
fs = generate()
for i in range(0, thread_num):
    t = threading.Thread(target=loop, name='LoopThread %s' % i, args=(fs,))
    t.start()
