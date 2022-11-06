---
layout: default
title: "document_venues | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity | Data](/Creativity) > [Tables](/Creativity/tables) > document_venues

# document_venues

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_venues`

显示名：

说明：论文出版物

更新日期：2022-10-02

## 字段明细

| **#** | **字段**           | **名称**     | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| ----- | ------------------ | ------------ | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | document_id        |              | INT          |          |          |            |              |
| 2     | venue_id           |              | INT          | √        | √        | 自增       |              |
| 3     | venue_display_name | 出版物展示名 | VARCHAR(511) |          |          |            |              |
| 4     | venue_name         | 出版物名     | VARCHAR(255) |          |          |            |              |
| 5     | venue_year         | 出版物年     | VARCHAR(8)   |          |          |            |              |
| 6     | venue_volume       | 出版物卷     | VARCHAR(16)  |          |          |            |              |
| 7     | venue_issue        | 出版物期     | VARCHAR(16)  |          |          |            |              |
| 8     | venue_page         | 出版物页     | VARCHAR(64)  |          |          |            |              |
| 9     | venue_type         | 出版物类型   | VARCHAR(255)  |          |          |            |              |
| 10    | data_source        | 数据源       | VARCHAR(255) |          |          |            |              |
| 11    | domain             |              | VARCHAR(255) |          |          |            |              |
| 12    | field              |              | VARCHAR(255) |          |          |            |              |
| 13    | subfield           |              | VARCHAR(255) |          |          |            |              |
| 14    | issn               |              | VARCHAR(16)  |          |          |            |              |

## 索引

|  #   |    字段     | 索引类型 | 索引方法 | 备注 |
| :--: | :---------: | :------: | :------: | :--: |
|  1   | document_id |  UNIQUE  |  BTREE   |      |
|  2   |  venue_id   |  NORMAL  |  BTREE   |      |
|  3   | venue_year  |  NORMAL  |  BTREE   |      |

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_venues;
CREATE TABLE document_venues(
    document_id INT    COMMENT '' ,
    venue_id INT NOT NULL AUTO_INCREMENT  COMMENT '' ,
    venue_display_name VARCHAR(511)    COMMENT '出版物展示名' ,
    venue_name VARCHAR(255)    COMMENT '出版物名' ,
    venue_year VARCHAR(8)    COMMENT '出版物年' ,
    venue_volume VARCHAR(16)    COMMENT '出版物卷' ,
    venue_issue VARCHAR(16)    COMMENT '出版物期' ,
    venue_page VARCHAR(16)    COMMENT '出版物页' ,
    venue_type VARCHAR(32)    COMMENT '出版物类型' ,
    data_source VARCHAR(255)    COMMENT '数据源' ,
    domain VARCHAR(255)    COMMENT '' ,
    field VARCHAR(255)    COMMENT '' ,
    subfield VARCHAR(255)    COMMENT '' ,
    issn VARCHAR(16)    COMMENT '' ,
    PRIMARY KEY (venue_id)
)  COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```

### 创建索引

```SQL
CREATE INDEX document_id ON document_venues(document_id);
CREATE INDEX venue_id ON document_venues(venue_id);
CREATE INDEX venue_year ON document_venues(venue_year);
```

## 更新日志

* 221002：标准化创建。
