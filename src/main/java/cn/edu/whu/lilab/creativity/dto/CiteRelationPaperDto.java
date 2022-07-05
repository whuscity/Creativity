package cn.edu.whu.lilab.creativity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "指定PMID的参考及引证DTO对象")
public class CiteRelationPaperDto {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "被引数")
    private Integer citeCount;


    @ApiModelProperty(value = "出版时间")
    private Integer publicationDate;

    @ApiModelProperty(value = "创新性指数")
    private Integer creativityIndex;


}
