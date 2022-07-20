package cn.edu.whu.lilab.creativity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询结果DTO对象")
public class SearchResultDto implements Serializable {

    @JsonProperty("document_id")
    @ApiModelProperty(value = "文档id")
    private String documentId;


    @ApiModelProperty(value = "外部id // pmid")
    private String externalId;


    @ApiModelProperty(value = "doi号")
    private String doi;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文档类型")
    private String documentType;

    @ApiModelProperty(value = "作者名字符串,分号分隔")
    private String authorsNameStr;

    @ApiModelProperty(value = "出版物信息")
    private String venueStr;

    @ApiModelProperty(value = "短摘要")
    private String abstractShort;

    @ApiModelProperty(value = "关键词字符串，分号分隔")
    private String keywordsStr;

    @ApiModelProperty(value = "被引数")
    private String citeCount;

    @ApiModelProperty(value = "发表时间")
    private Integer publishYear;


}
