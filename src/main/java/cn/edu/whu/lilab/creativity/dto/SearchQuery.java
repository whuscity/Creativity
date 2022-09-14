package cn.edu.whu.lilab.creativity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询条件DTO对象")
public class SearchQuery {
    @ApiModelProperty(value = "查询语句")
    private String query;

    @ApiModelProperty(value = "当前页，默认1")
    private Integer current;

    @ApiModelProperty(value = "每页显示条数，默认10")
    private Integer size;

    @ApiModelProperty(value = "排序依据(publish_year,cite_count,默认relevance)")
    private String orderType;

    @ApiModelProperty(value = "起始出版年")
    private Integer startPublishYear;

    @ApiModelProperty(value = "终止出版年")
    private Integer endPublishYear;

    @ApiModelProperty(value = "文档类型列表")
    private List<String> documentType;

    @ApiModelProperty(value = "出版物名列表")
    private List<String> venueName;

    @ApiModelProperty(value = "所属子领域列表")
    private List<String> subfield;



}
