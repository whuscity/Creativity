---
layout: default
title: "document_authors | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > document_authors

# document_authors

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`document_authors`

显示名：

说明：论文作者

更新日期：2022-11-09

## 字段明细

| **#** | **字段**                | **名称**     | **数据类型**  | **主键** | **索引** | **默认值** | **备注说明**             |
| ----- | ----------------------- | ------------ | ------------- | -------- | -------- | ---------- | ------------------------ |
| 1     | document_id             | id           | INT           | √        | `id` |            |                          |
| 2     | author_id               | 作者id       | INT           | √        | `author` | 自增       |                          |
| 3     | author_display_name     | 展示姓名     | VARCHAR(128)  |          |          |            |                          |
| 4     | author_rank             | 作者排序     | VARCHAR(8)    |          |          |            |                          |
| 5     | is_corresponding_author | 是否通讯作者 | VARCHAR(1)    |          |          |            | 1：通讯作者；0：其他作者 |
| 6     | is_first_author         | 是否第一作者 | VARCHAR(1)    |          |          |            | 1：第一作者；0：其他作者 |
| 7     | author_type             | 作者类型     | VARCHAR(8)    |          |          |            |                          |
| 8     | author_last_name        | 作者姓       | VARCHAR(64)  |          |          |            |                          |
| 9     | author_fore_name        | 作者名       | VARCHAR(64)  |          |          |            |                          |
| 10    | author_initials         | 作者名缩写   | VARCHAR(16)   |          |          |            |                          |
| 11    | author_affiliation_name | 作者单位     | TEXT  |          |          |            |            |
| 12    | author_address          | 作者地址     | TEXT |          |          |            |                          |
| 13    | author_email            | 作者电邮     | VARCHAR(128)  |          |          |            |                          |
| 14    | data_source             | 数据源       | VARCHAR(64)  |          |          |            |                          |

## 分区

无

## 代码

### 创建表

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
    author_last_name VARCHAR(64)    COMMENT '作者姓' ,
    author_fore_name VARCHAR(64)    COMMENT '作者名' ,
    author_initials VARCHAR(16)    COMMENT '作者名缩写' ,
    author_affiliation_name TEXT    COMMENT '作者单位' ,
    author_address TEXT    COMMENT '作者地址' ,
    author_email VARCHAR(128)    COMMENT '作者电邮' ,
    data_source VARCHAR(64)    COMMENT '数据源' ,
    PRIMARY KEY (document_id, author_id),
    KEY(author_id),
    INDEX id (document_id),
    INDEX author (author_id)
)  COMMENT = '';
```

## 更新日志

* 221109：删除分区。
* 221108：规范化索引。
* 221105：修改 `author_affiliation_name`、`author_address` 字段数据类型（`VARCHAR` -> `TEXT`）。
* 221002：标准化创建。
