package cn.edu.whu.lilab.creativity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchType {

    TITLE("title", "基于标题查询"),
    DOI("doi", "基于doi号查询"),
    PMID("external_id", "基于pmid号查询");


    private String code;
    private String desc;


}
