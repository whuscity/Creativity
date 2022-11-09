---
layout: default
title: "document_cite | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_cite

# document_cite

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_cite`

显示名：

说明：论文引用

更新日期：2022-11-09

## 字段明细

| **#** | **字段**           | **名称**   | **数据类型** | **主键** | **索引** | **默认值** | **备注说明** |
| ----- | ------------------ | --------- | ------------ | -------- | -------- | ---------- | ------------ |
| 1     | n                  |           | INT          |          |          | 自增       |              |
| 2     | cited_document_id  | 被引文档id | INT          | √        |  `cited`  |            |              |
| 3     | citing_document_id | 施引文档id | INT          | √        |  `citing`  |            |              |

## 分区

无

## 代码

### 创建表

```SQL
DROP TABLE IF EXISTS document_cite;
CREATE TABLE document_cite (
    n INT AUTO_INCREMENT  COMMENT '' ,
    cited_document_id INT    COMMENT '被引文档id' ,
    citing_document_id INT    COMMENT '施引文档id' ,
    PRIMARY KEY (cited_document_id,citing_document_id),
    KEY(n),
    INDEX cited(cited_document_id),
    INDEX citing(citing_document_id)
)  COMMENT = '';
```

## 更新日志

* 221109：调整分区。
* 221108：修改主键为引用列，自增列更名为 `n`，增加分区。规范化索引。
* 221002：标准化创建。
