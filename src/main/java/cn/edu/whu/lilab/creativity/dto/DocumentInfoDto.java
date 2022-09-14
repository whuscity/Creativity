package cn.edu.whu.lilab.creativity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "指定PMID的论文详情DTO对象")
public class DocumentInfoDto {

    @ApiModelProperty(value = "外部id // pmid")
    private String externalId; //需映射

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "doi号")
    private String doi;

    @ApiModelProperty(value = "发表时间")
    private String publishDate;

    @ApiModelProperty(value = "作者名字符串（所有作者名字拼接）")
    private String authorsNameStr;

    @ApiModelProperty(value = "短摘要")
    private String abstractShort;

    @ApiModelProperty(value = "关键词字符串（所有关键词拼接）// 明确分割规则")
    private String keywordsStr;

    @ApiModelProperty(value = "创新词")
    private String creativityWordsStr;


    @ApiModelProperty(value = "创新三元组")
    private String triplesStrList;

    @ApiModelProperty(value = "展示出版物信息")
    private String venueStr;
}
