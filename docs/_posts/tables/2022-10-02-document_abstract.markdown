---
layout: default
title: "document_abstract | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_abstract

# document_abstract

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_abstract`

显示名：

说明：文档结构化摘要

更新日期：2022-10-02

## 字段明细

| **#** |       **字段**       | **名称** | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| :---: | :------------------: | :------: | :----------: | :------: | :------: | :--------: | :----------: |
|   1   |     document_id      |    id    |     INT      |    √     |    √     |            |              |
|   2   |    abstract_full     | 摘要全文 |     TEXT     |          |          |            |              |
|   3   | abstract_background  | 背景部分 |     TEXT     |          |          |            |              |
|   4   |  abstract_objective  | 目标部分 |     TEXT     |          |          |            |              |
|   5   |   abstract_results   | 结果部分 |     TEXT     |          |          |            |              |
|   6   | abstract_conclusions | 结论部分 |     TEXT     |          |          |            |              |
|   7   |     data_source      |  数据源  | VARCHAR(255) |          |          |            |              |

## 索引

无

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_abstract;
CREATE TABLE document_abstract(
document_id INT NOT NULL COMMENT 'id' ,
abstract_full TEXT COMMENT '摘要全文' ,
abstract_background TEXT COMMENT '背景部分' ,
abstract_objective TEXT COMMENT '目标部分' ,
abstract_results TEXT COMMENT '结果部分' ,
abstract_conclusions TEXT COMMENT '结论部分' ,
data_source VARCHAR(255) COMMENT '数据源' ,
PRIMARY KEY (document_id)
) COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```



## 更新日志

* 221002：标准化创建。
