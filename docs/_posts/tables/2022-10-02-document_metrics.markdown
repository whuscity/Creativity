---
layout: default
title: "document_metrics | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

# document_metrics

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_metrics`

显示名：

说明：论文评价指标

更新日期：2022-10-02

## 字段明细

| **#** | **字段**                  | **名称**   | **数据类型** | **主键** | **非空** | **默认值** | **备注说明** |
| ----- | ------------------------- | ---------- | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | document_id               |            | INT          | √        | √        |            |              |
| 2     | document_cite_count       | 引用数     | VARCHAR(8)   |          |          |            |              |
| 3     | document_creativity_index | 创新性指数 | DOUBLE       |          |          |            |              |

## 索引

无

## 分区

分区数量：32

分区依据：`KEY()`

## 代码

### 创建表（含分区）

```SQL
DROP TABLE IF EXISTS document_metrics;
CREATE TABLE document_metrics(
document_id INT NOT NULL COMMENT '' ,
document_cite_count VARCHAR(8)COMMENT '引用数' ,
document_creativity_index DOUBLECOMMENT '创新性指数' ,
PRIMARY KEY (document_id)
)COMMENT = ''
PARTITION BY KEY()
PARTITIONS 32;
```

## 更新日志

* 221002：标准化创建。
