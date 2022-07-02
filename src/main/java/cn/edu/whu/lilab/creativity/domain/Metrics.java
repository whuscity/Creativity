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

@ApiModel(value = "metrics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "metrics")
public class Metrics {
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private Integer documentId;

    @TableField(value = "metric_value")
    @ApiModelProperty(value = "")
    private String metricValue;

    @TableField(value = "metric1_value")
    @ApiModelProperty(value = "")
    private String metric1Value;

    @TableField(value = "metric2_value")
    @ApiModelProperty(value = "")
    private String metric2Value;

    @TableField(value = "metric3_value")
    @ApiModelProperty(value = "")
    private String metric3Value;
}