package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;
import cn.edu.whu.lilab.creativity.dto.SearchResultDto;
import cn.edu.whu.lilab.creativity.enums.SearchType;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.naming.directory.SearchResult;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "搜索API")
@RestController
public class SearchController {

    @Autowired
    private DocumentsService documentsService;

    @GetMapping("/search")
    @ApiOperation(value = "基于指定查询语句分页搜索文献列表，按指定类型排序，默认相关度排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询语句"),
            @ApiImplicitParam(name = "current", value = "当前页，默认1", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认10", paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "relevance,publication_date,cite_count"),
    })
    public R<Page<SearchResultDto>> findSearchResult(String query, @ApiIgnore Page<SearchResultDto> page, String orderType) {
        // 判断查询类型：title、doi、pmid
        //doi: 10.+ 四位数字 + / + 无限制后缀
        Pattern doiPattern = Pattern.compile("^10\\.\\d{4}/.+$");
        //pmid: 全数字 最大八位
        Pattern pmidPattern = Pattern.compile("^\\d{1,8}$");


        if (doiPattern.matcher(query).matches()) { //doi查询
            SearchResultDto searchResultDtoByDoi = documentsService.findSearchResultByDoi(query);
            if (Objects.isNull(searchResultDtoByDoi)) {
                return R.fail("指定Doi号有误，请重新输入");
            }
            return R.ok(page.setRecords(Arrays.asList(searchResultDtoByDoi)).setTotal(1));
        } else if (pmidPattern.matcher(query).matches()) { //pmid查询
            SearchResultDto searchResultDtoByPmid = documentsService.findSearchResultByPmid(query);
            if (Objects.isNull(searchResultDtoByPmid)) {
                return R.fail("指定PubMed号有误，请重新输入");
            }
            return R.ok(page.setRecords(Arrays.asList(searchResultDtoByPmid)).setTotal(1));
        } else { //title查询 TODO
            Page<SearchResultDto> searchResultDtoPage = documentsService.findSearchResultByTitle(query, page, orderType);
            return R.ok();
        }


    }

    @GetMapping("/search")
    @ApiOperation(value = "没有提供查询语句，查询所有，默认相关度排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", paramType = "query"),
            @ApiImplicitParam(name = "orderType", value = "排序依据(默认creativity_index)", allowableValues = "relevance,publication_date,cite_count"),
    })
    public R<Page<SearchResultDto>> findSearchAllResultWithoutQuery(String query, @ApiIgnore Page<SearchResultDto> page, String orderType) {
        //  TODO

        return null;
    }
}
