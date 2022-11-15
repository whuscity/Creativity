from glob import glob
import os
from lxml import etree
import pandas as pd
from multiprocessing import Pool
import time
import gc
import sys

XML_DIRECTORY = r'/home/mariozzj/data/pubmed/'

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

def parse_file(filepath):
    filename = os.path.basename(filepath).replace('.xml','')
    print('\r'+filename,end=' start.   ')
    f = open(XML_DIRECTORY + filename + ".xml", 'rb')
    gc.collect()
    documents = open(XML_DIRECTORY + 'tables/documents/' + filename + '.tsv', 'w')
    document_abstract = open(XML_DIRECTORY + 'tables/document_abstract/' + filename + '.tsv', 'w')
    document_abstract_structure = open(XML_DIRECTORY + 'tables/document_abstract_structure/' + filename + '.tsv', 'w')
    document_authors = open(XML_DIRECTORY + 'tables/document_authors/' + filename + '.tsv', 'w')
    document_mesh = open(XML_DIRECTORY + 'tables/document_mesh/' + filename + '.tsv', 'w')
    document_keywords = open(XML_DIRECTORY + 'tables/document_keywords/' + filename + '.tsv', 'w')
    document_venues = open(XML_DIRECTORY + 'tables/document_venues/' + filename + '.tsv', 'w')
    root = etree.fromstring(f.read())
    f.close()
    del f
    gc.collect()
    articleSet = root.xpath('/PubmedArticleSet/PubmedArticle')
    for article in articleSet:
        if article.xpath('MedlineCitation/PMID/text()'):
            pmid = int(article.xpath('MedlineCitation/PMID/text()')[0])
        else: continue
        documentId = 100000000 + pmid
        publishDate = article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]/Year/text()')[0] + '/' + article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]/Month/text()')[0] + '/' + article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]/Day/text()')[0] if article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]') else ''
        publishYear = article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year/text()') else -1
        if publishYear == -1:
            publishYear = article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]/Year/text()')[0] if article.xpath('PubmedData/History/PubMedPubDate[@PubStatus="pubmed"]/Year/text()') else -1
        PublicationTypeList = article.xpath('MedlineCitation/Article/PublicationTypeList/PublicationType')
        documentType = '|'
        for PublicationType in PublicationTypeList:
            documentType += (PublicationType.text+'|')
        documentType = documentType[:-1]
        articleIds = article.xpath('PubmedData/ArticleIdList/ArticleId')
        doi = ''
        for aId in articleIds:
            if aId.get('IdType') == 'doi':
                doi = str(aId.text) if aId.text else ''
        articleTitle = article.xpath('MedlineCitation/Article/ArticleTitle/text()')[0] if article.xpath('MedlineCitation/Article/ArticleTitle/text()') else ''
        articleTitle = articleTitle.replace('\n',' ').replace('\t',' ')
        abstractTexts = article.xpath('MedlineCitation/Article/Abstract/AbstractText')
        i = 0
        abstractFull = ""
        abstractStructure = []
        for abstractText in abstractTexts:
            abstract = abstractText.text if abstractText.text else ''
            abstract = abstract.replace('\n',' ').replace('\t',' ')
            abstractFull += (abstract + ' ')
            if 'Label' in abstractText.attrib.keys():
                i += 1
                label = abstractText.attrib['Label']
                abstractStructure.append([str(documentId), str(i), abstract, label, filename])
        
        # keywords
        keywordSet = {}
        keywordMajorTopic = {}
        keywordList = []
        meshList = []
        meshHeadingList = article.xpath('MedlineCitation/MeshHeadingList/MeshHeading')
        for meshHeading in meshHeadingList:
            meshDescriptor = meshHeading.xpath('DescriptorName/text()')[0]
            meshDescriptorUI = meshHeading.xpath('DescriptorName/@UI')[0]
            meshDescriptorIsMajorTopic = meshHeading.xpath('DescriptorName/@MajorTopicYN')[0]
            meshQualifierNames = meshHeading.xpath('QualifierName')
            if meshQualifierNames:
                for meshQualifierName in meshQualifierNames:
                    meshQualifier = meshQualifierName.text if meshQualifierName.text else ''
                    meshQualifierUI = meshQualifierName.attrib['UI']
                    meshQualifierIsMajorTopic = meshQualifierName.attrib['MajorTopicYN']
                    keywordSet[meshQualifierUI] = meshQualifier
                    keywordMajorTopic[meshQualifierUI] =  meshQualifierIsMajorTopic
                    meshList.append([str(documentId),str(int(meshDescriptorUI[1:])),meshDescriptorIsMajorTopic,str(int(meshQualifierUI[1:])),meshQualifierIsMajorTopic,filename])
                del meshQualifierNames
            else:
                meshList.append([str(documentId),str(int(meshDescriptorUI[1:])),meshDescriptorIsMajorTopic,'-1','N',filename])
            keywordSet[meshDescriptorUI] = meshDescriptor
            keywordMajorTopic[meshDescriptorUI] = meshDescriptorIsMajorTopic
        for key in keywordSet.keys():
            keywordList.append([str(documentId), 'MeshHeading', key, keywordSet[key], keywordMajorTopic[key], filename])
        keywordSet = {}
        keywordMajorTopic = {}

        KeywordList = article.xpath('MedlineCitation/KeywordList/Keyword')
        KeywordOwner = article.xpath('MedlineCitation/KeywordList/@Owner')[0] if article.xpath('MedlineCitation/KeywordList') else ''
        i = 0
        keywordsStr = "|"
        for kwd in KeywordList:
            keyword = kwd.text if kwd.text else ""
            if keyword:
                i += 1
                keywordSet[KeywordOwner+str(i)] = keyword
                keywordsStr += (keyword + '|')
        keywordsStr = keywordsStr[1:-1]
        for key in keywordSet.keys():
            keywordList.append([str(documentId), 'Keyword'+KeywordOwner, key, keywordSet[key], 'N', filename])
        keywordSet = {}

        ChemicalList = article.xpath('MedlineCitation/ChemicalList/Chemical')
        for Chemical in ChemicalList:
            chemical = Chemical.xpath('NameOfSubstance/text()')[0]
            chemicalUI = Chemical.xpath('NameOfSubstance/@UI')[0]
            keywordSet[chemicalUI] = chemical
        for key in keywordSet.keys():
            keywordList.append([str(documentId), 'Chemical', key, keywordSet[key], 'N', filename])
        keywordSet = {}

        # author
        AuthorList = article.xpath('MedlineCitation/Article/AuthorList/Author')
        authorList = []
        rank = 0
        authorsNameStr = ', '
        for Author in AuthorList:
            rank += 1
            authorLastName = Author.xpath('LastName/text()')[0] if Author.xpath('LastName/text()') else ''
            authorForeName = Author.xpath('ForeName/text()')[0] if Author.xpath('ForeName/text()') else ''
            authorInitials = Author.xpath('Initials/text()')[0] if Author.xpath('Initials/text()') else ''
            authorAffiliationName = Author.xpath('AffiliationInfo/Affiliation/text()')[0] if Author.xpath('AffiliationInfo/Affiliation/text()') else ''
            authorName = authorInitials + ' ' + authorLastName
            authorsNameStr += (authorName + ', ')
            authorList.append([str(documentId), authorName, str(rank), authorLastName, authorForeName, authorInitials, authorAffiliationName, filename])
        authorsNameStr = authorsNameStr[2:-2]

        # journal
        issn = article.xpath('MedlineCitation/Article/Journal/ISSN/text()')[0].replace('-','') if article.xpath('MedlineCitation/Article/Journal/ISSN/text()') else -1
        domain = ''
        field = ''
        subfield = ''
        if issn in issn_match.keys():
            domain = issn_match[issn]["domain"]
            field = issn_match[issn]["field"]
            subfield = issn_match[issn]["subfield"]
        venueName = article.xpath('MedlineCitation/Article/Journal/Title/text()')[0] if article.xpath('MedlineCitation/Article/Journal/Title') else ''
        venueVolume = article.xpath('MedlineCitation/Article/Journal/JournalIssue/Volume/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/Volume') else ''
        venueIssue = article.xpath('MedlineCitation/Article/Journal/JournalIssue/Issue/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/Issue') else ''
        venuePage = article.xpath('MedlineCitation/Article/Pagination/MedlinePgn/text()')[0] if article.xpath('MedlineCitation/Article/Pagination/MedlinePgn/text()') else ''
        venueYear = article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year/text()')[0] if article.xpath('MedlineCitation/Article/Journal/JournalIssue/PubDate/Year') else -1
        venueDisplayName = ''
        if venueName:
            venueDisplayName += (venueName + '. ')
            if venueYear != -1:
                venueDisplayName += (venueYear + ';')
                if venueVolume:
                    venueDisplayName += venueVolume
                    if venueIssue:
                        venueDisplayName += ('(' + venueIssue + ')')
                        if venuePage:
                            venueDisplayName += (':' + venuePage)
        
        # doctype
        PublicationTypeList = article.xpath('MedlineCitation/Article/PublicationTypeList/PublicationType')
        documentType = '|'
        for PublicationType in PublicationTypeList:
            documentType += PublicationType.text if PublicationType.text else ''
            documentType += '|'
        documentType = documentType[1:-1]
        
        documents.write('\t'.join([str(documentId),str(pmid),'pmid',articleTitle[:255],authorsNameStr[:255],documentType[:255],venueDisplayName[:255],abstractFull[:511],keywordsStr[:511],doi,publishDate[:128],str(publishYear),filename])+'\n')
        document_abstract.write(str(documentId)+'\t'+abstractFull+'\n')
        for abs in abstractStructure:
            document_abstract_structure.write('\t'.join(abs)+'\n')
        for author in authorList:
            document_authors.write('\t'.join(author)+'\n')
        for mesh in meshList:
            document_mesh.write('\t'.join(mesh)+'\n')
        for keyword in keywordList:
            document_keywords.write('\t'.join(keyword)+'\n')
        document_venues.write('\t'.join([str(documentId),venueDisplayName[:511],venueName[:255],str(venueYear),str(venueVolume),str(venueIssue),venuePage[:64],filename,domain,field,subfield,str(issn)])+'\n')
        del PublicationTypeList, articleIds, abstractTexts, meshHeadingList, KeywordList, ChemicalList, AuthorList
        gc.collect()
    del root,articleSet
    documents.close()
    document_abstract.close()
    document_abstract_structure.close()
    document_authors.close()
    document_mesh.close()
    document_keywords.close()
    document_venues.close()
    del documents,document_abstract,document_abstract_structure,document_authors,document_mesh,document_keywords,document_venues
    gc.collect()
    return filename

def multi_worker(filepaths, workers):
    with Pool(processes=workers) as pool: 
        print(pool.map(parse_file, filepaths))

if __name__ == '__main__':
    if len(sys.argv) == 2:
        workers = int(sys.argv[1])
    else:
        workers = 8
    filepaths = sorted(list(glob(XML_DIRECTORY + r'*.xml')))
    multi_worker(filepaths, workers)

    
