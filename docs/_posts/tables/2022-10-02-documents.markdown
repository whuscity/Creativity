---
layout: default
title: "documents | table"
date: 2022-10-02 14:16:29 +0800
categories: tables
---

> [Creativity-Data](/Creativity) > [Tables](/Creativity/tables) > documents

# documents

![Author](https://img.shields.io/badge/Author-MarioZZJ-blue)

表名：`documents`

显示名：

说明：文档基础信息

更新日期：2022-11-09

## 字段明细

| **#** |       **字段**       |    **名称**    | **数据类型** | **主键** | **索引** | **默认值** |        **备注说明**        |
| :---: | :------------------: | :------------: | :----------: | :------: | :------: | :--------: | :------------------------: |
|   1   |     document_id      |       id       |     INT      |    √     |  `id`    |            |                            |
|   2   |     external_id      |    外部 id     | VARCHAR(32)  |          | `eid`    |            |                            |
|   3   |   external_id_type   |  外部 id 类型  | VARCHAR(32)  |          |          |            |                            |
|   4   |        title         |    文档标题    | VARCHAR(255) |          |          |            |                            |
|   5   |   authors_name_str   |  展示作者姓名  | VARCHAR(511) |          |          |            | 规则：多个项目时，`|`分隔 |
|   6   |    document_type     |    文档类型    | VARCHAR(255) |          |          |            |                            |
|   7   |      venue_str       | 展示出版物信息 | VARCHAR(255) |          |          |            |                            |
|   8   |    abstract_short    |    展示摘要    | VARCHAR(511) |          |          |            |                            |
|   9   |     keywords_str     |   展示关键词   | VARCHAR(511) |          |          |            | 规则：多个项目时，`|`分隔 |
|  10   |         doi          |                | VARCHAR(64)  |          | `doi`    |            |                            |
|  11   |     publish_date     |    发表时间    | VARCHAR(128) |          |          |            |                            |
|  12   |     publish_year     |    发表年份    |     INT      | √        | `py`     |            |                            |
|  13   |      cite_count      |     被引量     |     INT      |          |          |            |                            |
|  14   |   creativity_index   |   创新性指数   |    DOUBLE    |          |          |            |                            |
|  15   | creativity_words_str |   展示创新词   | VARCHAR(255) |          |          |            |                            |
|  16   |   triples_str_list   |   创新三元组   | VARCHAR(255) |          |          |            |                            |
|  17   |     data_source      |     数据源     | VARCHAR(64) |          |          |            |                            |

## 分区

依据列 `publish_year` 分区存储，查询时建议使用 `publish_year` 进行限定。分区分别为：
1. `p1970`（`publish_year` 小于 1980）
2. `p1980`（`publish_year` 小于 1990）
3. `p1990`（`publish_year` 小于 2000）
4. `p0005`（`publish_year` 小于 2006）
5. `p0610`（`publish_year` 小于 2011）
6. `p1115`（`publish_year` 小于 2016）
7. `p1620`（`publish_year` 小于 2020）
8. `p2125`（`publish_year` 小于 2026）

## 代码

### 创建表

```SQL
DROP TABLE IF EXISTS documents;
CREATE TABLE documents(
    document_id INT NOT NULL   COMMENT 'id' ,
    external_id VARCHAR(32)    COMMENT '外部 id' ,
    external_id_type VARCHAR(32)    COMMENT '外部 id 类型' ,
    title VARCHAR(255)    COMMENT '文档标题' ,
    authors_name_str VARCHAR(511)    COMMENT '展示作者姓名;规则：多个项目时，分号分隔' ,
    document_type VARCHAR(255)    COMMENT '文档类型;规则：多个项目时，|分隔' ,
    venue_str VARCHAR(255)    COMMENT '展示出版物信息' ,
    abstract_short VARCHAR(511)    COMMENT '展示摘要' ,
    keywords_str VARCHAR(511)    COMMENT '展示关键词;规则：多个项目时，分号分隔' ,
    doi VARCHAR(64)    COMMENT '' ,
    publish_date VARCHAR(32)    COMMENT '发表时间' ,
    publish_year INT    COMMENT '发表年份' ,
    cite_count INT    COMMENT '被引量' ,
    creativity_index DOUBLE    COMMENT '创新性指数' ,
    creativity_words_str VARCHAR(255)    COMMENT '展示创新词' ,
    triples_str_list VARCHAR(255)    COMMENT '创新三元组' ,
    data_source VARCHAR(64)    COMMENT '数据源' ,
    PRIMARY KEY (document_id,publish_year),
    INDEX id (document_id),
    INDEX eid (external_id),
    INDEX py (publish_year),
    INDEX doi (doi)
)  COMMENT = ''
PARTITION BY RANGE COLUMNS(publish_year) (
    PARTITION p1970 VALUES LESS THAN (1980),
    PARTITION p1980 VALUES LESS THAN (1990),
    PARTITION p1990 VALUES LESS THAN (2000),
    PARTITION p0005 VALUES LESS THAN (2006),
    PARTITION p0610 VALUES LESS THAN (2011),
    PARTITION p1115 VALUES LESS THAN (2016),
    PARTITION p1620 VALUES LESS THAN (2021),
    PARTITION p2125 VALUES LESS THAN (2026)
);
```

## 更新日志

* 221109：调整分区，仅保留 `publish_year`。
* 221108：规范化索引。
* 221106：统一分隔符为 `|`。
* 221002：标准化创建。
