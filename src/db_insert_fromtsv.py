import pymysql
import json
import sys
import multiprocessing
from glob import glob

load_data_sql = """
    LOAD DATA LOCAL INFILE '{}'
    INTO TABLE {}
    FIELDS TERMINATED BY '\t'
    LINES TERMINATED BY '\n'
    ({});
"""

dbconfig = json.load(open('./db.config'))

schema = {
    'documents':'document_id, external_id, external_id_type, title, authors_name_str, document_type, venue_str, abstract_short, keywords_str, doi, publish_date, publish_year, data_source',
    'document_abstract_structure':'document_id, sentence_id, texts, label, data_source',
    'document_abstract': 'document_id, abstract_full',
    'document_authors': 'document_id, author_display_name, author_rank, author_last_name, author_fore_name, author_initials, author_affiliation_name, data_source',
    'document_keywords': 'document_id, keyword_type, keyword_id, keyword, major_topic_yn, data_source',
    'document_venues': 'document_id, venue_display_name, venue_name, venue_year, venue_volume, venue_issue, venue_page, data_source, domain, field, subfield, issn',
    'document_mesh': 'document_id, descriptor_ui, descriptor_is_major_topic, qualifier_ui, qualifier_is_major_topic, data_source'
}



def load_data(tableName, filepath):
    db = pymysql.connect(host=dbconfig.get('host'), user=dbconfig.get('user'), password=dbconfig.get('password'), db=dbconfig.get('db'), local_infile=True)
    cursor = db.cursor()
    linesAffected = cursor.execute(load_data_sql.format(filepath,tableName,schema[tableName]))
    print('\t'.join([tableName, filepath, str(linesAffected)]))
    db.commit()
    db.close()

def load_data_many(tableName, filepaths):
    for filepath in filepaths:
        load_data(tableName, filepath)


if __name__ == '__main__':
    tableName = sys.argv[1]
    NUM_WORKER = int(sys.argv[2])
    BASEDIR = '/home/mariozzj/data/pubmed/tables'
    filepaths = sorted(list(glob(BASEDIR + '/' + tableName + '/*.tsv')))
    worker_len = int(len(filepaths)/NUM_WORKER) + 1
    processList = []
    for i in range(NUM_WORKER):
        p = multiprocessing.Process(target=load_data_many, args=(tableName,filepaths[worker_len*i:worker_len*(i+1)],))
        processList.append(p)
    for process in processList:
        process.start()
    for process in processList:
        process.join()