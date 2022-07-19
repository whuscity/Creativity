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

@ApiModel(value = "document_authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_authors")
public class DocumentAuthors {
    /**
     * 作者id
     */
    @TableId(value = "author_id", type = IdType.INPUT)
    @ApiModelProperty(value = "作者id")
    private Integer authorId;

    /**
     * id
     */
    @TableField(value = "document_id")
    @ApiModelProperty(value = "id")
    private Integer documentId;

    /**
     * 展示姓名
     */
    @TableField(value = "author_display_name")
    @ApiModelProperty(value = "展示姓名")
    private String authorDisplayName;

    /**
     * 作者排序
     */
    @TableField(value = "author_rank")
    @ApiModelProperty(value = "作者排序")
    private String authorRank;

    /**
     * 是否通讯作者;1：通讯作者；0：其他作者
     */
    @TableField(value = "is_corresponding_author")
    @ApiModelProperty(value = "是否通讯作者;1：通讯作者；0：其他作者")
    private String isCorrespondingAuthor;

    /**
     * 是否第一作者;1：第一作者；0：其他作者
     */
    @TableField(value = "is_first_author")
    @ApiModelProperty(value = "是否第一作者;1：第一作者；0：其他作者")
    private String isFirstAuthor;

    /**
     * 作者类型
     */
    @TableField(value = "author_type")
    @ApiModelProperty(value = "作者类型")
    private String authorType;

    /**
     * 作者姓
     */
    @TableField(value = "author_last_name")
    @ApiModelProperty(value = "作者姓")
    private String authorLastName;

    /**
     * 作者名
     */
    @TableField(value = "author_fore_name")
    @ApiModelProperty(value = "作者名")
    private String authorForeName;

    /**
     * 作者名缩写
     */
    @TableField(value = "author_initials")
    @ApiModelProperty(value = "作者名缩写")
    private String authorInitials;

    /**
     * 作者单位;规则：分号分隔
     */
    @TableField(value = "author_affiliation_name")
    @ApiModelProperty(value = "作者单位;规则：分号分隔")
    private String authorAffiliationName;

    /**
     * 作者地址
     */
    @TableField(value = "author_address")
    @ApiModelProperty(value = "作者地址")
    private String authorAddress;

    /**
     * 作者电邮
     */
    @TableField(value = "author_email")
    @ApiModelProperty(value = "作者电邮")
    private String authorEmail;
}