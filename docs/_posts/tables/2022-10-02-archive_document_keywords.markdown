---
layout: default
title: "document_keywords | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

# document_keywords

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_keywords`

显示名：

说明：论文关键词

更新日期：2022-10-02

## 字段明细

| **#** | **字段**         | **名称**   | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| ----- | ---------------- | ---------- | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | document_id      | 文档 id    | INT          |          |          |            |              |
| 2     | keyword_id       | 关键词 id  | INT          | √        | √        | 自增       |              |
| 3     | keyword_str      | 关键词     | VARCHAR(255) |          |          |            |              |
| 4     | keyword_type     | 关键词类型 | VARCHAR(32)  |          |          |            |              |
| 5     | keyword_ext_info | 附加信息   | TEXT         |          |          |            |              |
| 6     | data_source      | 数据源     | VARCHAR(255) |          |          |            |              |

## 索引

|  #   |    字段     | 索引类型 | 索引方法 | 备注 |
| :--: | :---------: | :------: | :------: | :--: |
|  1   | document_id |  NORMAL  |  BTREE   |      |

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_keywords;
CREATE TABLE document_keywords(
document_id INTCOMMENT '文档 id' ,
keyword_id INT NOT NULL AUTO_INCREMENTCOMMENT '关键词 id' ,
keyword_str VARCHAR(255)COMMENT '关键词' ,
keyword_type VARCHAR(32)COMMENT '关键词类型' ,
keyword_ext_info TEXTCOMMENT '附加信息' ,
data_source VARCHAR(255)COMMENT '数据源' ,
PRIMARY KEY (keyword_id)
)COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```

### 创建索引

```SQL
CREATE INDEX document_id ON document_keywords(document_id);
```

## 更新日志

* 221002：标准化创建。
