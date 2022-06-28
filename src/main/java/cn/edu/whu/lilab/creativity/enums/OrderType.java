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
    PUB_DATE("publish_date","论文发表事件排序"),
    CITED_YEAR("cited_document_year","参考文献按发表年排序"),
    CITING_YEAR("citing_document_year","引证文献按发表年排序"),
    CITE_COUNT("cite_count","论文引用数排序");

    private String code;
    private String desc;

    public String getValue(){
        return this.code;
    }

}
