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

@ApiModel(value="documents_cite")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "documents_cite")
public class DocumentsCite {
    /**
     * 被引文献id
     */
    @TableId(value = "cited_document_id", type = IdType.INPUT)
    @ApiModelProperty(value="被引文献id")
    private Integer citedDocumentId;

    /**
     * 施引文献id
     */
    @TableId(value = "citing_document_id", type = IdType.INPUT)
    @ApiModelProperty(value="施引文献id")
    private Integer citingDocumentId;

    @TableField(value = "cited_document_year")
    @ApiModelProperty(value="")
    private Integer citedDocumentYear;

    @TableField(value = "citing_document_year")
    @ApiModelProperty(value="")
    private Integer citingDocumentYear;
}