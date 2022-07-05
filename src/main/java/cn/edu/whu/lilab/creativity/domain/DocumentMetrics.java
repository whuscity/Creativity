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

@ApiModel(value="document_metrics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_metrics")
public class DocumentMetrics {
    @TableField(value = "document_id")
    @ApiModelProperty(value="")
    private Integer documentId;

    /**
     * 引用数
     */
    @TableField(value = "document_cite_count")
    @ApiModelProperty(value="引用数")
    private String documentCiteCount;

    /**
     * 创新性指数
     */
    @TableField(value = "document_creativity_index")
    @ApiModelProperty(value="创新性指数")
    private Double documentCreativityIndex;
}