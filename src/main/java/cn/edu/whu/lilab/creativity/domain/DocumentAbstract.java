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

@ApiModel(value = "document_abstract")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_abstract")
public class DocumentAbstract {
    /**
     * id
     */
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer documentId;

    /**
     * 摘要全文
     */
    @TableField(value = "abstract_full")
    @ApiModelProperty(value = "摘要全文")
    private String abstractFull;

    /**
     * 背景部分
     */
    @TableField(value = "abstract_background")
    @ApiModelProperty(value = "背景部分")
    private String abstractBackground;

    /**
     * 方法部分
     */
    @TableField(value = "abstract_method")
    @ApiModelProperty(value = "方法部分")
    private String abstractMethod;

    /**
     * 结论部分
     */
    @TableField(value = "abstract_conclusion")
    @ApiModelProperty(value = "结论部分")
    private String abstractConclusion;

    @TableField(value = "data_source")
    @ApiModelProperty(value = "")
    private String dataSource;
}