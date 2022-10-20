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

local_obj = threading.local()

sql_document = """
INSERT INTO documents
(document_id, external_id, external_id_type, title, authors_name_str, document_type, venue_str, abstract_short, doi, publish_date, publish_year, keywords_str, data_source)
VALUES
(%s, %s,'pmid',%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
"""

sql_document_abstract = """
INSERT INTO document_abstract
(document_id, abstract_full, data_source)
VALUES
(%s,%s,%s)
"""

sql_document_abstract_structure = """
INSERT INTO document_abstract_structure
(document_id, sentence_id, texts, label, data_source)
VALUES
(%s,%s,%s,%s,%s)
"""

sql_document_authors = """
INSERT INTO document_authors
(document_id, author_display_name, author_rank, author_last_name, author_fore_name, author_initials, author_affiliation_name, data_source)
VALUES
(%s, %s, %s, %s, %s, %s, %s,%s)
"""

sql_document_venues = """
INSERT INTO document_venues
(document_id, venue_display_name, venue_name, venue_year, venue_volume, venue_issue, venue_page, venue_type,data_source,issn,domain,field,subfield)
VALUES
(%s, %s, %s, %s, %s, %s, %s, %s,%s,%s,%s,%s,%s)
"""

sql_document_keywords = """
INSERT INTO document_keywords
(document_id, keyword_str, data_source)
VALUES
(%s, %s,%s)
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
                        maxconnections=0,  # 连接池允许的最大连接数，0和None表示不限制连接数
                        mincached=0,  # 初始化时，链接池中至少创建的空闲的链接，0表示不创建
                        maxcached=None,  # 链接池中最多闲置的链接，0和None不限制
                        maxusage=None,  # 一个链接最多被重复使用的次数，None表示无限制
                        blocking=True,  # 连接池中如果没有可用连接后，是否阻塞等待。True，等待；False，不等待然后报错
                        host='127.0.0.1',  # 此处必须是是127.0.0.1
                        port=3306,
                        user='cradmin',
                        passwd='CRadmin.888',
                        db='creativity_web',
                        charset='utf8mb4')
        return pool
 
    def save_mysql(self, sql, arg, filename):
        """
        保存数据库
        :param sql: 执行sql语句 str
        :param arg: 添加的sql语句的参数 list[]
        """
        id = None
        try:
            db = self.pool.connection()  # 连接数据池
            cursor = db.cursor()  # 获取游标
            cursor.execute(sql,arg)
            id = cursor.lastrowid
            db.commit()
        except:
            logging.exception("error in save_mysql at file " + filename)
            db.rollback()
        finally:
            cursor.close()
            db.close()
            return id

    def save_mysql_many(self, sql, args, filename):
        """
        保存数据库
        :param sql: 执行sql语句 str
        :param arg: 添加的sql语句的参数 list[tuple()]
        """
        try:
            db = self.pool.connection()  # 连接数据池
            cursor = db.cursor()  # 获取游标
            cursor.executemany(sql,args)
            db.commit()
        except:
            logging.exception("error in save_mysql_many at file " + filename)
            db.rollback()
        finally:
            cursor.close()
            db.close()


    def parse_files(self, filepaths):
        for filepath in filepaths:
            gc.collect()
            filename = filepath[-20:-7]
            with gzip.open(XML_DIRECTORY + filename + ".xml.gz", 'rb') as f:
                root = etree.fromstring(f.read())
                articleSet = root.xpath('/PubmedArticleSet/PubmedArticle')
                local_obj.abstract_insert = []
                local_obj.abstract_structure_insert = []
                local_obj.venues_insert = []
                local_obj.keywords_insert = []
                local_obj.author_insert = []
                local_obj.document_insert = []
                gc.collect()

                for article in articleSet:
                    pmid = int(article.xpath('MedlineCitation/PMID[@Version="1"]/text()')[0]) if article.xpath('MedlineCitation/PMID[@Version="1"]/text()') else -1
                    journal_volume = article.xpath('MedlineCitation/Article/Journal/JournalIssue/Volume/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/Volume/text()') else ''
                    journal_issue = article.xpath('MedlineCitation/Article/Journal/JournalIssue/Issue/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/Issue/text()') else ''
                    j_p_year = article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year/text()') else ''
                    j_p_month = article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Month/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Month/text()') else ''
                    j_p_day = article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Day/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Day/text()') else ''
                    journal_pubdate = j_p_year
                    if j_p_month:
                        journal_pubdate += ('.' + j_p_month)
                        if j_p_day:
                            journal_pubdate += ('.' + j_p_day)
                    if j_p_year == '':
                        j_p_year = -1
                    issn = article.xpath('MedlineCitation/Article/Journal/ISSN/text()')[0].replace('-','') if article.xpath('MedlineCitation/Article/Journal/ISSN/text()') else ''
                    domain = ''
                    field = ''
                    subfield = ''
                    if issn in issn_match.keys():
                        domain = issn_match[issn]["domain"]
                        field = issn_match[issn]["field"]
                        subfield = issn_match[issn]["subfield"]
                    
                    journal_title = article.xpath('MedlineCitation/Article/Journal/Title/text()')[0] if article.xpath('MedlineCitation/Article/Journal/Title/text()') else ''
                    pagination = article.xpath('MedlineCitation/Article/Pagination/MedlinePgn/text()')[0] if article.xpath('MedlineCitation/Article/Pagination/MedlinePgn/text()') else ''

                    if journal_volume != '':
                        venue_str = journal_title + '. ' + journal_pubdate + '. Vol ' + journal_volume + ', Issue ' + journal_issue
                    else:
                        venue_str = journal_title + '. ' + journal_pubdate
                    
                    article_title = article.xpath('MedlineCitation/Article/ArticleTitle/text()')[0] if article.xpath('MedlineCitation/Article/ArticleTitle/text()') else ''
                    authors = article.xpath('MedlineCitation/Article/AuthorList/Author')
                    authors_name = ""
                    author_info_list = []
                    author_index = 0
                    for author in authors:
                        author_index += 1
                        authors_name += "; "
                        fn = author.xpath('ForeName/text()')[0] if author.xpath('ForeName/text()') else ''
                        authors_name += fn
                        authors_name += ' '
                        lf = author.xpath('LastName/text()')[0] if author.xpath('LastName/text()') else ''
                        authors_name += lf
                        ini = author.xpath('Initials/text()')[0] if author.xpath('Initials/text()') else ''
                        af = author.xpath('AffiliationInfo/Affiliation/text()')[0] if author.xpath('AffiliationInfo/Affiliation/text()') else ''
                        author_info = [fn + ' ' + lf,author_index,lf,fn,ini,af]
                        author_info_list.append(author_info)
                    authors_name = authors_name[2:]
                    abstractTexts = article.xpath('MedlineCitation/Article/Abstract/AbstractText')
                    i = 0
                    abstract_text = ""
                    for abstractText in abstractTexts:
                        abstract = abstractText.text if abstractText.text else ''
                        abstract_text += (abstract + ' ')
                        if 'Label' in abstractText.attrib.keys():
                            i += 1
                            label = abstractText.attrib['Label']
                            abstract_structure = [(100000000 + pmid), i, abstract, label, filename]
                            local_obj.abstract_structure_insert.append(abstract_structure)
                    abstract_text = abstract_text[:-1]

                    KeywordList = article.xpath('MedlineCitation/KeywordList/Keyword')
                    keywords_str = ""
                    keywords_list = []
                    for keyword in KeywordList:
                        keywords_str += "; "
                        keywords_str += keyword.text if keyword.text else ""
                        if keyword.text:
                            keywords_list.append(keyword.text[:255])
                    keywords_str = keywords_str[2:] if len(keywords_str) > 1 else ""

                    articleIds = article.xpath('PubmedData/ArticleIdList/ArticleId')
                    doi = ''
                    for aId in articleIds:
                        if aId.get('IdType') == 'doi':
                            doi = str(aId.text) if aId.text else ''

                    PublicationTypes = article.xpath('MedlineCitation/Article/PublicationTypeList/PublicationType')
                    publication_type = ""
                    for pType in PublicationTypes:
                        publication_type += "|"
                        publication_type += pType.text if pType.text else ""
                    publication_type = publication_type[1:] if len(publication_type) > 0 else ""

                    if pmid == -1:
                        continue
                    else: 
                        id = 100000000 + pmid
                    try:
                        data = [id, pmid,
                        article_title[:255],
                        authors_name[:511],
                        publication_type[:255],
                        venue_str[:255],
                        abstract_text[:511],
                        doi[:64],
                        journal_pubdate,
                        j_p_year,
                        keywords_str[:511],filename]
                        local_obj.document_insert.append(data)
                        if len(local_obj.document_insert) >= 950:
                            self.save_mysql_many(sql_document, local_obj.document_insert, filename)
                            local_obj.document_insert = []
                            gc.collect()

                        local_obj.abstract_insert.append((id,abstract_text,filename))

                        if len(local_obj.abstract_structure_insert) >= 950:
                            self.save_mysql_many(sql_document_abstract_structure, local_obj.abstract_structure_insert, filename)
                            local_obj.abstract_structure_insert = []
                            gc.collect()

                        if len(local_obj.abstract_insert) >= 950:
                            self.save_mysql_many(sql_document_abstract,local_obj.abstract_insert, filename)
                            local_obj.abstract_insert = []
                            gc.collect()

                        for author_info in author_info_list:
                            local_obj.author_insert.append((id, author_info[0],author_info[1],author_info[2],author_info[3],author_info[4], author_info[5][:511],filename))

                        if len(local_obj.author_insert) >= 950:
                            self.save_mysql_many(sql_document_authors,local_obj.author_insert, filename)
                            local_obj.author_insert = []
                            gc.collect()
        
                        local_obj.venues_insert.append((id, venue_str[:511], journal_title[:255], j_p_year, journal_volume[:16], journal_issue[:16], pagination[:32], publication_type,filename,issn,domain,field,subfield))

                        if len(local_obj.venues_insert) >= 950:
                            self.save_mysql_many(sql_document_venues,local_obj.venues_insert, filename)
                            local_obj.venues_insert = []
                            gc.collect()
                        
                        for keyword in keywords_list:
                            local_obj.keywords_insert.append((id, keyword,filename))
                            
                        if len(local_obj.keywords_insert) >= 950:
                            self.save_mysql_many(sql_document_keywords,local_obj.keywords_insert, filename)
                            local_obj.keywords_insert = []
                            gc.collect()
                    
                    except Exception as e:
                        logging.exception("error in for_article at file " + filename)
                        continue
                self.save_mysql_many(sql_document_abstract,local_obj.abstract_insert, filename)
                self.save_mysql_many(sql_document_authors,local_obj.author_insert, filename)
                self.save_mysql_many(sql_document_venues,local_obj.venues_insert, filename)
                self.save_mysql_many(sql_document_keywords,local_obj.keywords_insert, filename)
                self.save_mysql_many(sql_document, local_obj.document_insert, filename)
                gc.collect()
    
    def multi_worker(self, filepaths, workers):
        threads = []
        for i in range(workers):
            threads.append(threading.Thread(target=self.parse_files,args=[filepaths[i::workers]],daemon=False))
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
                    gc.collect()
                if len(threads) == 0:
                    break

if __name__ == '__main__':
    XML_DIRECTORY = r'/home/mariozzj/data/pubmed/'
    filepaths = sorted(list(glob(XML_DIRECTORY + r'*.gz')))

    issn_classification = pd.read_csv("/home/mariozzj/data/issn_classification.csv")
    issn_match = {}
    for index,row in issn_classification.iterrows():
        if row.issn1 == 'article-level classification':pass
        else:
            issn = str(row.issn1)
            domain = str(row.domain)
            field = str(row.field)
            subfield = str(row.subfield)
            issn_match[issn] = {
                "domain": domain,
                "field": field,
                "subfield": subfield,
            }
            if str(row.issn2) != 'nan':
                issn2 = str(row.issn2)
                issn_match[issn2] = {
                    "domain": domain,
                    "field": field,
                    "subfield": subfield,
                }
                if str(row.issn3) != 'nan':
                    issn3 = str(row.issn3)
                    issn_match[issn3] = {
                        "domain": domain,
                        "field": field,
                        "subfield": subfield,
                    }
    b = Base()
    sem=threading.Semaphore(24)
    b.multi_worker(filepaths, 24)
    
