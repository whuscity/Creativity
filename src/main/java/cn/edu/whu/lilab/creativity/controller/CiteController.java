package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.enums.OrderType;
import cn.edu.whu.lilab.creativity.model.CiteRelationPaper;
import cn.edu.whu.lilab.creativity.Vo.CiteRelationPaperVo;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsCiteService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "参考及引证API")
@RestController
public class CiteController {

    @Autowired
    private DocumentsCiteService documentsCiteService;

    @GetMapping("/ref/{pmid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmid", value = "PubMed号"),
            @ApiImplicitParam(name = "current", value = "当前页",paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "creativity_index,year,cite_count"),
    })
    public R<Page<CiteRelationPaperVo>> findById(@PathVariable String pmid, @ApiIgnore Page<CiteRelationPaperVo> page, String orderType) {
        // 没传入排序类型时，默认创新指数排序
        if (orderType == null) orderType = OrderType.CREATIVITY_INDEX.getValue();
        Page<CiteRelationPaperVo> citeRelationPaperListVo = documentsCiteService.findById(pmid, page, orderType);

        String message = null;

        if (!citeRelationPaperListVo.hasNext()) {
            message = "已翻至最后一页";
        }
        if (citeRelationPaperListVo.getTotal() == 0) {
            message = "pmid有误或本身无参考文献";
        }
        return R.ok(citeRelationPaperListVo, message);

    }
}
