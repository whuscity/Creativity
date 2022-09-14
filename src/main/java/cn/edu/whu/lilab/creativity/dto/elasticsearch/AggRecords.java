package cn.edu.whu.lilab.creativity.dto.elasticsearch;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询结果对应聚合记录")
public class AggRecords {

    @ApiModelProperty(value = "聚合字段值")
    private String key;

    @ApiModelProperty(value = "聚合数量")
    private Long count;
}
