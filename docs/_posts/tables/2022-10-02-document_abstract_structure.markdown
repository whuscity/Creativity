---
layout: default
title: "document_abstract_structure | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_abstract_structure

# document_abstract_structure

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_abstract_structure`

显示名：

说明：文档摘要成分（Pubmed 自带信息）

更新日期：2022-11-09

## 字段明细

| **#** |  **字段**   | **名称** | **数据类型** | **主键** | **索引** | **默认值** | **备注说明** |
| :---: | :---------: | :------: | :----------: | :------: | :------: | :--------: | :----------: |
|   1   | document_id |    id    |     INT      |    √     | `id`  |            |              |
|   2   | sentence_id | 内容顺序 |     INT      |    √     |        |            |              |
|   3   |    texts    | 内容全文 |     TEXT     |          |          |            |              |
|   4   |    label    | 内容类型 | VARCHAR(64)  |          |          |            |              |
|   5   | data_source |  数据源  | VARCHAR(64) |          |          |            |              |

## 分区

无

## 代码

### 创建表

```SQL
DROP TABLE IF EXISTS document_abstract_structure;
CREATE TABLE document_abstract_structure(
    document_id INT NOT NULL   COMMENT 'id' ,
    sentence_id INT NOT NULL   COMMENT '内容顺序' ,
    texts TEXT    COMMENT '内容全文' ,
    label VARCHAR(64)    COMMENT '内容类型' ,
    data_source VARCHAR(64)    COMMENT '数据源' ,
    PRIMARY KEY (document_id,sentence_id),
    INDEX id(document_id)
)  COMMENT = '';
```

## 更新日志

* 221109：删除分区。
* 221108：规范化索引。
* 221105：修改 `label` 字段数据类型（`TEXT` -> `VARCHAR(64)`）。
* 221002：标准化创建。
