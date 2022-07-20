package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.constants.EsConstants;
import cn.edu.whu.lilab.creativity.dto.SearchResultDto;
import cn.edu.whu.lilab.creativity.elasticsearch.QueryResult;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.regex.Pattern;

@Api(tags = "搜索API")
@RestController
@CrossOrigin()
public class SearchController {

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    private QueryResult queryResult;

    @GetMapping("/search")
    @ApiOperation(value = "基于指定查询语句分页搜索文献列表，按指定类型排序，默认相关度排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询语句"),
            @ApiImplicitParam(name = "current", value = "当前页，默认1", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认10", paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "relevance,publish_year,cite_count")
    })
    public R<Page<SearchResultDto>> findSearchResult(String query, @ApiIgnore Page<SearchResultDto> page, String orderType) {
        // 只支持查询前1000条数据

        if (page.getCurrent() * page.getSize() > EsConstants.MAX_RESULT_COUNT) {
            return R.fail(String.format("仅支持查询前%d条数据，建议缩小查询范围", EsConstants.MAX_RESULT_COUNT));
        }

        // 判断查询类型：title、doi、pmid
        //doi: 10.+ 四位数字 + / + 无限制后缀
        Pattern doiPattern = Pattern.compile("^10\\.\\d{4}/.+$");
        //pmid: 全数字 最大八位
        Pattern pmidPattern = Pattern.compile("^\\d{1,8}$");

        //防止抛异常
        if (StringUtils.isEmpty(query)) {
            query = "";
        }

        if (doiPattern.matcher(query).matches()) { //doi查询
             queryResult.searchByDoi(query, page,orderType);
            // if (CollectionUtils.isEmpty(page.getRecords()) || page.getRecords().size() == 0) {
            //     return R.fail("指定Doi号有误，请重新输入");
            // }

        } else if (pmidPattern.matcher(query).matches()) { //pmid查询
            queryResult.searchByPmid(query,page, orderType);
            // if (CollectionUtils.isEmpty(page.getRecords()) || page.getRecords().size() == 0) {
            //     return R.fail("指定PubMed号有误，请重新输入");
            // }

        } else { //title查询
            queryResult.searchByTitle(query, page, orderType);
            // if (CollectionUtils.isEmpty(page.getRecords()) || page.getRecords().size() == 0) {
            //     return R.fail("无相关数据，请检查是否拼写有误");
            // }

        }

        if (CollectionUtils.isEmpty(page.getRecords()) || page.getRecords().size() == 0) {
            return R.fail("未查询到相关结果");
        }
        return R.ok(page);


    }



}
