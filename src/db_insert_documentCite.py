import pymysql
import logging
import json

dbconfig = json.load(open('./dbconfig.json'))

db = pymysql.connect(host=dbconfig.get('host'), user=dbconfig.get('user'), password=dbconfig.get('password'), db=dbconfig.get('db'))
cursor = db.cursor()

sql_document_cite = """
INSERT INTO document_cite
(cited_document_id, citing_document_id)
VALUES
(%s,%s)
"""

with open(r'/home/mariozzj/data/icite_NIH/open_citation_collection.csv') as f:
    data_list = []
    lineStr = f.readline()
    i = 0
    while True:
        lineStr = f.readline()
        if not lineStr: break
        cited_id, citing_id = lineStr.strip().split(',')
        cited_document_id   = int(cited_id)  + 100000000
        citing_document_id  = int(citing_id) + 100000000
        data_list.append((cited_document_id, citing_document_id))
        i += 1
        if len(data_list) >= 999:
            try:
                cursor.executemany(sql_document_cite,data_list)
                db.commit()
                print("\r" + str(i) + " lines inserted.", end="")
            except:
                logging.exception("error ")
                db.rollback()
                continue
            finally:
                data_list = []
    db.close()