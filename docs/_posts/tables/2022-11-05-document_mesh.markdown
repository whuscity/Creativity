---
layout: default
title: "document_mesh | table"
date: 2022-11-05 20:00:00 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_mesh

# document_mesh

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_mesh`

显示名：

说明：论文医学主题词表

更新日期：2022-11-08

## 字段明细

| **#** | **字段**                  | **名称**                | **数据类型** | **主键** | **索引** | **默认值** | **备注说明** |
| ----- | ------------------------  | ---------------------- | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | document_id               | 文档 id                | INT          | √        |  `id`   |            |              |
| 2     | descriptor_ui             | MeSH Discriptor UI     | INT          | √        |  `dui`  |            |              |
| 3     | descriptor_is_major_topic | descriptor 是主要主题   | VARCHAR(1)   |          |          |            |              |
| 4     | qualifier_ui              | MeSH Qualifier  UI     | INT          | √        |  `qui`  |            |              |
| 5     | qualifier_is_major_topic  | qualifier 是主要主题    | VARCHAR(1)   |          |          |            |              |
| 6     | data_source               | 数据源                  | VARCHAR(64) |          |          |            |              |

## 分区

分区数量：32

分区依据：`KEY()`

## 其他说明

* **MeSH UI 形如 `X000000`，其中 descriptor 首位为 D，qualifier 首位为 Q，本表中的 ui 均为去除首位字母后转换的数值，故都为 INT 类型。**
* 若 descriptor 无任何 qualifier，则 qualifier ui 为 -1。

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_keywords;
CREATE TABLE document_keywords(
document_id INT COMMENT '文档 id' ,
descriptor_ui INT COMMENT 'MeSH Discriptor UI',
descriptor_is_major_topic VARCHAR(1) COMMENT 'descriptor 是主要主题',
qualifier_ui INT COMMENT 'MeSH Qualifier UI',
qualifier_is_major_topic VARCHAR(1) COMMENT 'MajorTopicYN',
data_source VARCHAR(64) COMMENT '数据源' ,
PRIMARY KEY (document_id,descriptor_ui,qualifier_ui),
INDEX id (document_id),
INDEX dui (descriptor_ui),
INDEX qui (qualifier_ui)
) COMMENT = '论文医学主题词表'
PARTITION BY KEY()
PARTITIONS 32;
```

## 更新日志

* 221108：规范化索引。
* 221105：根据 MeSH 特性重构表，存储预处理后数据，提升性能。
* [221025](/Creativity/tables/2022/10/25/archive_document_mesh.html)：标准化创建。
