package cn.edu.whu.lilab.creativity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排序类型
 */
@AllArgsConstructor
@Getter
public enum OrderType {

    RELEVANCE("_score","相关度排序"),
    CREATIVITY_INDEX("creativity_index","创新指数排序"),
    PUBLICATION_YEAR("publish_year","论文发表年排序"),
    CITE_COUNT("cite_count","论文引用数排序");


    private String code;
    private String desc;



}
