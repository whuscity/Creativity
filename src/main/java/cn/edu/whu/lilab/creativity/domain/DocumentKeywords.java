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

@ApiModel(value = "document_keywords")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_keywords")
public class DocumentKeywords {
    /**
     * 关键词 id
     */
    @TableId(value = "keyword_id", type = IdType.INPUT)
    @ApiModelProperty(value = "关键词 id")
    private Integer keywordId;

    /**
     * 文档 id
     */
    @TableField(value = "document_id")
    @ApiModelProperty(value = "文档 id")
    private Integer documentId;

    /**
     * 关键词
     */
    @TableField(value = "keyword_str")
    @ApiModelProperty(value = "关键词")
    private String keywordStr;

    /**
     * 关键词类型
     */
    @TableField(value = "keyword_type")
    @ApiModelProperty(value = "关键词类型")
    private String keywordType;

    /**
     * 附加信息
     */
    @TableField(value = "keyword_ext_info")
    @ApiModelProperty(value = "附加信息")
    private String keywordExtInfo;
}