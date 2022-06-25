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

@ApiModel(value="document_abstract")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_abstract")
public class DocumentAbstract {
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Integer documentId;

    @TableField(value = "abstract_full")
    @ApiModelProperty(value="")
    private String abstractFull;

    @TableField(value = "abstract_background")
    @ApiModelProperty(value="")
    private String abstractBackground;

    @TableField(value = "abstract_method")
    @ApiModelProperty(value="")
    private String abstractMethod;

    @TableField(value = "abstract_conclusion")
    @ApiModelProperty(value="")
    private String abstractConclusion;
}