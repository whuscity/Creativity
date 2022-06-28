package cn.edu.whu.lilab.creativity.model;

import cn.edu.whu.lilab.creativity.Vo.CiteRelationPaperVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "参考及引用返回对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiteRelationPaper {

    @ApiModelProperty(value = "总页数")
    private Long totalPage;

    @ApiModelProperty(value = "指定页的VO对象")
    private Page<CiteRelationPaperVo> citeRelationPaperListVo;

}
