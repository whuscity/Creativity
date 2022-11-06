---
layout: default
title: "document_cite | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity | Data](/Creativity) > [Tables](/Creativity/tables) > document_cite

# document_cite

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_cite`

显示名：

说明：论文引用

更新日期：2022-10-02

## 字段明细

| **#** | **字段**           | **名称**   | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| ----- | ------------------ | ---------- | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | id                 |            | INT          | √        | √        | 自增       |              |
| 2     | cited_document_id  | 被引文档id | INT          |          |          |            |              |
| 3     | citing_document_id | 施引文档id | INT          |          |          |            |              |

## 索引

|  #   |        字段        | 索引类型 | 索引方法 | 备注 |
| :--: | :----------------: | :------: | :------: | :--: |
|  1   | cited_document_id  |  NORMAL  |  BTREE   |      |
|  2   | citing_document_id |  NORMAL  |  BTREE   |      |

## 分区

无

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_cite;
CREATE TABLE document_cite(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '' ,
    cited_document_id INT    COMMENT '被引文档id' ,
    citing_document_id INT    COMMENT '施引文档id' ,
    PRIMARY KEY (id)
)  COMMENT = '';
```

### 创建索引

```SQL
CREATE INDEX cited_document_id ON document_cite(cited_document_id);
CREATE INDEX citing_document_id ON document_cite(citing_document_id);
```

## 更新日志

* 221002：标准化创建。
