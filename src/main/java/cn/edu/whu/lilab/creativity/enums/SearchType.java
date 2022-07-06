package cn.edu.whu.lilab.creativity.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum SearchType {

    TITLE("title", "基于标题查询"),
    DOI("doi", "基于doi号查询"),
    PMID("pmid", "基于pmid号查询");


    private String code;
    private String desc;


}
