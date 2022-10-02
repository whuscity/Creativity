---
layout: default
title: "document_authors | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

# document_authors

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_authors`

显示名：

说明：论文作者

更新日期：2022-10-02

## 字段明细

| **#** | **字段**                | **名称**     | **数据类型**  | **主键** | **非空** | **默认值** | **备注说明**             |
| ----- | ----------------------- | ------------ | ------------- | -------- | -------- | ---------- | ------------------------ |
| 1     | document_id             | id           | INT           |          |          |            |                          |
| 2     | author_id               | 作者id       | INT           | √        | √        | 自增       |                          |
| 3     | author_display_name     | 展示姓名     | VARCHAR(128)  |          |          |            |                          |
| 4     | author_rank             | 作者排序     | VARCHAR(8)    |          |          |            |                          |
| 5     | is_corresponding_author | 是否通讯作者 | VARCHAR(1)    |          |          |            | 1：通讯作者；0：其他作者 |
| 6     | is_first_author         | 是否第一作者 | VARCHAR(1)    |          |          |            | 1：第一作者；0：其他作者 |
| 7     | author_type             | 作者类型     | VARCHAR(8)    |          |          |            |                          |
| 8     | author_last_name        | 作者姓       | VARCHAR(128)  |          |          |            |                          |
| 9     | author_fore_name        | 作者名       | VARCHAR(128)  |          |          |            |                          |
| 10    | author_initials         | 作者名缩写   | VARCHAR(16)   |          |          |            |                          |
| 11    | author_affiliation_name | 作者单位     | VARCHAR(511)  |          |          |            | 规则：分号分隔           |
| 12    | author_address          | 作者地址     | VARCHAR(1023) |          |          |            |                          |
| 13    | author_email            | 作者电邮     | VARCHAR(128)  |          |          |            |                          |
| 14    | data_source             | 数据源       | VARCHAR(255)  |          |          |            |                          |

## 索引

|  #   |    字段     | 索引类型 | 索引方法 | 备注 |
| :--: | :---------: | :------: | :------: | :--: |
|  1   | document_id |  NORMAL  |  BTREE   |      |
|  2   |  author_id  |  NORMAL  |  BTREE   |      |

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_authors;
CREATE TABLE document_authors(
    document_id INT    COMMENT 'id' ,
    author_id INT NOT NULL AUTO_INCREMENT  COMMENT '作者id' ,
    author_display_name VARCHAR(128)    COMMENT '展示姓名' ,
    author_rank VARCHAR(8)    COMMENT '作者排序' ,
    is_corresponding_author VARCHAR(1)    COMMENT '是否通讯作者;1：通讯作者；0：其他作者' ,
    is_first_author VARCHAR(1)    COMMENT '是否第一作者;1：第一作者；0：其他作者' ,
    author_type VARCHAR(8)    COMMENT '作者类型' ,
    author_last_name VARCHAR(128)    COMMENT '作者姓' ,
    author_fore_name VARCHAR(128)    COMMENT '作者名' ,
    author_initials VARCHAR(16)    COMMENT '作者名缩写' ,
    author_affiliation_name VARCHAR(511)    COMMENT '作者单位;规则：分号分隔' ,
    author_address VARCHAR(1023)    COMMENT '作者地址' ,
    author_email VARCHAR(128)    COMMENT '作者电邮' ,
    data_source VARCHAR(255)    COMMENT '数据源' ,
    PRIMARY KEY (author_id)
)  COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```

### 创建索引

```SQL
CREATE INDEX document_id ON document_authors(document_id);
CREATE INDEX author_id ON document_authors(author_id);
```

## 更新日志

* 221002：标准化创建。
