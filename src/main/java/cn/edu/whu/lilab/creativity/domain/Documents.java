package cn.edu.whu.lilab.creativity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "documents")
public class Documents {
    /**
     * // 需加索引
     */
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "// 需加索引")
    private Integer documentId;

    /**
     * 外部id // 可能非纯数字
     */
    @TableField(value = "external_id")
    @ApiModelProperty(value = "外部id // 可能非纯数字")
    private String externalId;

    /**
     * 外部id来源，如pmid // 考虑多库
     */
    @TableField(value = "external_id_type")
    @ApiModelProperty(value = "外部id来源，如pmid // 考虑多库")
    private String externalIdType;

    @TableField(value = "title")
    @ApiModelProperty(value = "")
    private String title;

    /**
     * 作者名字符串（所有作者名字拼接）
     */
    @TableField(value = "authors_name_str")
    @ApiModelProperty(value = "作者名字符串（所有作者名字拼接）")
    private String authorsNameStr;

    /**
     * 文章类型 // 规定好枚举
     */
    @TableField(value = "document_type")
    @ApiModelProperty(value = "文章类型 // 规定好枚举")
    private String documentType;

    /**
     * 出版物名称（如期刊）
     */
    @TableField(value = "venue_str")
    @ApiModelProperty(value = "出版物名称（如期刊）")
    private String venueStr;

    /**
     * 短摘要
     */
    @TableField(value = "abstract_short")
    @ApiModelProperty(value = "短摘要")
    private String abstractShort;

    /**
     * 关键词字符串（所有关键词拼接）// 明确分割规则
     */
    @TableField(value = "keywords_str")
    @ApiModelProperty(value = "关键词字符串（所有关键词拼接）// 明确分割规则")
    private String keywordsStr;

    /**
     * 引用数
     */
    @TableField(value = "cite_count")
    @ApiModelProperty(value = "引用数")
    private Integer citeCount;

    /**
     * // 需加索引
     */
    @TableField(value = "doi")
    @ApiModelProperty(value = "// 需加索引")
    private String doi;

    /**
     * 创新性指数
     */
    @TableField(value = "creativity_index")
    @ApiModelProperty(value = "创新性指数")
    private Integer creativityIndex;
}