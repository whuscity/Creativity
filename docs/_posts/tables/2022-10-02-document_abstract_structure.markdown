---
layout: default
title: "document_abstract_structure | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity | Data](/Creativity) > [Tables](/Creativity/tables) > document_abstract_structure

# document_abstract_structure

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_abstract_structure`

显示名：

说明：文档摘要成分（Pubmed 自带信息）

更新日期：2022-11-05

## 字段明细

| **#** |  **字段**   | **名称** | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| :---: | :---------: | :------: | :----------: | :------: | :------: | :--------: | :----------: |
|   1   | document_id |    id    |     INT      |    √     |    √     |            |              |
|   2   | sentence_id | 内容顺序 |     INT      |    √     |    √     |            |              |
|   3   |    texts    | 内容全文 |     TEXT     |          |          |            |              |
|   4   |    label    | 内容类型 | VARCHAR(64)  |          |          |            |              |
|   5   | data_source |  数据源  | VARCHAR(255) |          |          |            |              |

## 索引

无

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_abstract_structure;
CREATE TABLE document_abstract_structure(
    document_id INT NOT NULL   COMMENT 'id' ,
    sentence_id INT NOT NULL   COMMENT '内容顺序' ,
    texts TEXT    COMMENT '内容全文' ,
    label VARCHAR(64)    COMMENT '内容类型' ,
    data_source VARCHAR(255)    COMMENT '数据源' ,
    PRIMARY KEY (document_id,sentence_id)
)  COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```



## 更新日志

* 221105：修改 `label` 字段数据类型（`TEXT` -> `VARCHAR(64)`）。
* 221002：标准化创建。
