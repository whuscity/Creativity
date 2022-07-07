package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.enums.OrderType;
import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentCiteService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "参考及引证API")
@RestController
public class CiteController {

    @Autowired
    private DocumentCiteService documentCiteService;

    @GetMapping("/ref/{pmid}")
    @ApiOperation(value = "分页获取指定PMID的参考文献列表，按指定类型排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmid", value = "PubMed号"),
            @ApiImplicitParam(name = "current", value = "当前页",paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "creativity_index,publication_date,cite_count"),
    })
    public R<Page<CiteRelationPaperDto>> findRefById(@PathVariable String pmid, @ApiIgnore Page<CiteRelationPaperDto> page, String orderType) {
        // 没传入排序类型时，默认创新指数排序
        if (StringUtils.isEmpty(orderType)) orderType = OrderType.CREATIVITY_INDEX.getCode();
        Page<CiteRelationPaperDto> citeRelationPaperListVo = documentCiteService.findRefById(pmid, page, orderType);

        String message = null;

        if (!citeRelationPaperListVo.hasNext()) {
            message = "已翻至最后一页";
        }
        if (citeRelationPaperListVo.getTotal() == 0) {
            message = "pmid有误或本身无参考文献";
        }
        return R.ok(citeRelationPaperListVo, message);

    }

    @GetMapping("/citing/{pmid}")
    @ApiOperation(value = "分页获取指定PMID的引证文献列表，按指定类型排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmid", value = "PubMed号"),
            @ApiImplicitParam(name = "current", value = "当前页",paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "creativity_index,publish_date,cite_count"),
    })
    public R<Page<CiteRelationPaperDto>> findCitingById(@PathVariable String pmid, @ApiIgnore Page<CiteRelationPaperDto> page, String orderType) {
        // 没传入排序类型时，默认创新指数排序
        if (orderType == null) orderType = OrderType.CREATIVITY_INDEX.getCode();
        Page<CiteRelationPaperDto> citeRelationPaperListVo = documentCiteService.findCitingById(pmid, page, orderType);

        String message = null;

        if (!citeRelationPaperListVo.hasNext()) {
            message = "已翻至最后一页";
        }
        if (citeRelationPaperListVo.getTotal() == 0) {
            message = "pmid有误或本身无引证文献";
        }
        return R.ok(citeRelationPaperListVo, message);

    }
}
