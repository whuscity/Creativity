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

@ApiModel(value = "document_cite")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_cite")
public class DocumentCite {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 被引文档id
     */
    @TableField(value = "cited_document_id")
    @ApiModelProperty(value = "被引文档id")
    private Integer citedDocumentId;

    /**
     * 施引文档id
     */
    @TableField(value = "citing_document_id")
    @ApiModelProperty(value = "施引文档id")
    private Integer citingDocumentId;
}