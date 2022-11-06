---
layout: default
title: "document_mesh | table"
date: 2022-10-25 12:41:00 +0800
categories: tables
---

# document_mesh

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_mesh`

显示名：

说明：论文医学主题词表

更新日期：2022-10-25

## 字段明细

| **#** | **字段**          | **名称**                        | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| ----- | ----------------  | ------------------------------ | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | document_id       | 文档 id                        | INT          | √        | √        |            |              |
| 2     | mesh_ui           | MeSH Discriptor UI             | VARCHAR(16)  | √        | √       |             |              |
| 3     | mesh              | MeSH Discriptor                | VARCHAR(255) |          |          |            |              |
| 4     | mesh_qualifier_ui | MeSH Qualifier UI; `|` 分隔多个 | VARCHAR(255) |          |          |            |              |
| 5     | is_major_topic    | MajorTopicYN                   | VARCHAR(1)   |          |          |            |              |
| 6     | data_source       | 数据源                         | VARCHAR(255) |          |          |            |              |

## 索引

|  #   |    字段     | 索引类型 | 索引方法 | 备注 |
| :--: | :---------: | :------: | :------: | :--: |
|  1   | document_id |  NORMAL  |  BTREE   |      |
|  2   | mesh_ui     |  NORMAL  |  BTREE   |      |

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_keywords;
CREATE TABLE document_keywords(
document_id INT NOT NULL COMMENT '文档 id' ,
mesh_ui VARCHAR(16) NOT NULL COMMENT 'MeSH Discriptor UI', 
mesh VARCHAR(255) COMMENT 'MeSH Discriptor',
mesh_qualifier_ui VARCHAR(255) COMMENT 'MeSH Qualifier UI; | 分隔多个',
is_major_topic VARCHAR(1) COMMENT 'MajorTopicYN',
data_source VARCHAR(255)COMMENT '数据源' ,
PRIMARY KEY (document_id,mesh_ui)
)COMMENT = '论文医学主题词表'
PARTITION BY KEY()
PARTITIONS 32;
```

### 创建索引

```SQL
CREATE INDEX document_id ON document_mesh(document_id);
CREATE INDEX mesh_ui ON document_mesh(mesh_ui);
```

## 更新日志

* 221025：标准化创建。
