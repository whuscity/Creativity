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
     * id
     */
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer documentId;

    /**
     * 外部 id
     */
    @TableField(value = "external_id")
    @ApiModelProperty(value = "外部 id")
    private String externalId;

    /**
     * 外部 id 类型
     */
    @TableField(value = "external_id_type")
    @ApiModelProperty(value = "外部 id 类型")
    private String externalIdType;

    /**
     * 文档标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "文档标题")
    private String title;

    /**
     * 展示作者姓名;规则：多个项目时，分号分隔
     */
    @TableField(value = "authors_name_str")
    @ApiModelProperty(value = "展示作者姓名;规则：多个项目时，分号分隔")
    private String authorsNameStr;

    /**
     * 文档类型
     */
    @TableField(value = "document_type")
    @ApiModelProperty(value = "文档类型")
    private String documentType;

    /**
     * 展示出版物信息
     */
    @TableField(value = "venue_str")
    @ApiModelProperty(value = "展示出版物信息")
    private String venueStr;

    /**
     * 展示摘要
     */
    @TableField(value = "abstract_short")
    @ApiModelProperty(value = "展示摘要")
    private String abstractShort;

    /**
     * 展示关键词;规则：多个项目时，分号分隔
     */
    @TableField(value = "keywords_str")
    @ApiModelProperty(value = "展示关键词;规则：多个项目时，分号分隔")
    private String keywordsStr;

    @TableField(value = "doi")
    @ApiModelProperty(value = "")
    private String doi;

    /**
     * 发表时间
     */
    @TableField(value = "publish_date")
    @ApiModelProperty(value = "发表时间")
    private String publishDate;

    /**
     * 发表年份
     */
    @TableField(value = "publish_year")
    @ApiModelProperty(value = "发表年份")
    private Integer publishYear;

    /**
     * 被引量
     */
    @TableField(value = "cite_count")
    @ApiModelProperty(value = "被引量")
    private String citeCount;

    /**
     * 创新性指数
     */
    @TableField(value = "creativity_index")
    @ApiModelProperty(value = "创新性指数")
    private Double creativityIndex;

    /**
     * 展示创新词
     */
    @TableField(value = "creativity_words_str")
    @ApiModelProperty(value = "展示创新词")
    private String creativityWordsStr;

    /**
     * 创新三元组
     */
    @TableField(value = "triples_str_list")
    @ApiModelProperty(value = "创新三元组")
    private String triplesStrList;

    /**
     * 其他信息
     */
    @TableField(value = "data_source")
    @ApiModelProperty(value = "其他信息")
    private String dataSource;
}