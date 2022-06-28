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

@ApiModel(value = "documents_authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "documents_authors")
public class DocumentsAuthors {
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private Integer documentId;

    @TableField(value = "author_id")
    @ApiModelProperty(value = "")
    private Integer authorId;

    @TableField(value = "author_name")
    @ApiModelProperty(value = "")
    private String authorName;

    @TableField(value = "author_rank")
    @ApiModelProperty(value = "")
    private Integer authorRank;

    /**
     * // 规范对应01，下同
     */
    @TableField(value = "is_corresponding_author")
    @ApiModelProperty(value = "// 规范对应01，下同")
    private Integer isCorrespondingAuthor;

    @TableField(value = "is_first_author")
    @ApiModelProperty(value = "")
    private Integer isFirstAuthor;

    /**
     * // 明确简写、缩写、forename等
     */
    @TableField(value = "author_type")
    @ApiModelProperty(value = "// 明确简写、缩写、forename等")
    private String authorType;

    /**
     * // 后续考虑normailize
     */
    @TableField(value = "author_affiliation_name")
    @ApiModelProperty(value = "// 后续考虑normailize")
    private String authorAffiliationName;

    /**
     * // 可能是aff地址，可能是其他地址
     */
    @TableField(value = "author_address")
    @ApiModelProperty(value = "// 可能是aff地址，可能是其他地址")
    private String authorAddress;

    @TableField(value = "author_email")
    @ApiModelProperty(value = "")
    private String authorEmail;
}