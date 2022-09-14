package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.constants.EsConstants;
import cn.edu.whu.lilab.creativity.dto.SearchQuery;
import cn.edu.whu.lilab.creativity.dto.SearchResult;
import cn.edu.whu.lilab.creativity.elasticsearch.QueryResult;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索API")
@RestController
@CrossOrigin()
public class SearchController {

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    private QueryResult queryResult;

    @PostMapping("/search")
    @ApiOperation(value = "基于指定查询语句分页搜索文献列表，按指定类型排序，默认相关度排序", produces = "application/json", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询语句"),
            @ApiImplicitParam(name = "current", value = "当前页，默认1"),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认10"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(publish_year,cite_count,默认relevance)", allowableValues = "relevance,publish_year,cite_count"),
            @ApiImplicitParam(name = "startPublishYear", value = "起始出版年（包括）"),
            @ApiImplicitParam(name = "endPublishYear", value = "终止出版年（包括）"),
            @ApiImplicitParam(name = "documentType", value = "文档类型list"),
            @ApiImplicitParam(name = "venueName", value = "出版物名list"),
            @ApiImplicitParam(name = "subfield", value = "所属子领域list")
    })
    public R<SearchResult> findSearchResult(@RequestBody SearchQuery searchQuery) {
        // 只支持查询前1000条数据

        if (searchQuery.getCurrent() * searchQuery.getSize() > EsConstants.MAX_RESULT_COUNT) {
            return R.fail(String.format("仅支持查询前%d条数据，建议缩小查询范围", EsConstants.MAX_RESULT_COUNT));
        }
        if (searchQuery.getSize() == 0) {
            return R.fail("不合法的页面记录数");
        }

        SearchResult searchResult = queryResult.search(searchQuery);


        if (searchResult.getHitTotal() == null) {
            return R.fail("查询出错，请重试");
        }
        if (searchResult.getHitTotal() == 0) {
            return R.ok(searchResult, "未查询到相关结果");
        }
        return R.ok(searchResult);


    }


}
