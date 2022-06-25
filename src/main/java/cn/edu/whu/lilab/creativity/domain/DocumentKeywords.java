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

@ApiModel(value="document_keywords")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_keywords")
public class DocumentKeywords {
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Integer documentId;

    @TableField(value = "keyword_id")
    @ApiModelProperty(value="")
    private Integer keywordId;

    @TableField(value = "keyword_str")
    @ApiModelProperty(value="")
    private String keywordStr;

    @TableField(value = "keyword_type")
    @ApiModelProperty(value="")
    private String keywordType;

    @TableField(value = "keyword_ext_info")
    @ApiModelProperty(value="")
    private String keywordExtInfo;
}