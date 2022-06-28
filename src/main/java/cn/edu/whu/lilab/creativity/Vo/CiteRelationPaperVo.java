package cn.edu.whu.lilab.creativity.Vo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "指定PMID的参考及引证VO对象")
public class CiteRelationPaperVo {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "被引数")
    private Integer citeCount;


    @ApiModelProperty(value = "出版年")
    private Integer year;

    @ApiModelProperty(value = "创新性指数")
    private Integer creativityIndex;


}
