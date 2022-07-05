package cn.edu.whu.lilab.creativity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 排序类型
 */
@AllArgsConstructor
@Getter
public enum OrderType {

    RELEVANCE("relevance","相关度排序"),
    CREATIVITY_INDEX("creativity_index","创新指数排序"),
    PUBLICATION_DATE("publish_date","论文发表时间排序"),
    CITE_COUNT("cite_count","论文引用数排序");

    private String code;
    private String desc;

    public String getValue(){
        return this.code;
    }

}
