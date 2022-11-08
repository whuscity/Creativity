---
layout: default
title: "document_keywords | table"
date: 2022-11-05 20:00:00 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_keywords

# document_keywords

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_keywords`

显示名：

说明：论文关键词

更新日期：2022-11-05

## 字段明细

| **#** | **字段**         | **名称**   | **数据类型** | **主键** | **非空** | **默认值** | **备注说明**  |
| ----- | ---------------- | ---------- | ----------- | -------- | -------- | ---------- | ----------- |
| 1     | document_id      | 文档 id   | INT           |   √    |          |            |              |
| 2     | keyword_type     | 关键词类型 | VARCHAR(32)  |    √    |          |             |             |
| 3     | keyword_id       | 关键词 id  | VARCHAR(32)  |   √     |          |            | 若源不含 UI，则按本文序列生成，格式 `TYPE_I` |
| 4     | keyword          | 关键词     | VARCHAR(64) |         |          |            |              |
| 5     | major_topic_yn   | 是主要主题 | VARCHAR(1)   |         |          |            |              |
| 6     | data_source      | 数据源     | VARCHAR(64) |         |          |           |               |

## 索引

|  #   |    字段      | 索引类型 | 索引方法 | 备注 |
| :--: | :----------: | :------: | :------: | :--: |
|  1   | document_id  |  NORMAL  |  BTREE   |      |
|  2   | keyword_type |  NORMAL  |  BTREE   |      |
|  3   | keyword_id   |  NORMAL  |  BTREE   |      |

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_keywords;
CREATE TABLE document_keywords(
document_id INT COMMENT '文档 id' ,
keyword_type VARCHAR(32) COMMENT '关键词类型',
keyword_id VARCHAR(32) COMMENT '关键词 id',
keyword VARCHAR(64) COMMENT '关键词',
major_topic_yn VARCHAR(1) COMMENT '是主要主题',
data_source VARCHAR(64) COMMENT '数据源' ,
PRIMARY KEY (document_id, keyword_type, keyword_id)
) COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```

### 创建索引

```SQL
CREATE INDEX document_id ON document_keywords(document_id);
CREATE INDEX keyword_type ON document_keywords(keyword_type);
CREATE INDEX keyword_id ON document_keywords(keyword_id);
```

## 更新日志

* 221105：考虑多源（Mesh、Chemical、Keyword、Pubtator）词特性，统一重构表。
* [221002](/Creativity/tables/2022/10/02/archive_document_keywords.html)：标准化创建。
