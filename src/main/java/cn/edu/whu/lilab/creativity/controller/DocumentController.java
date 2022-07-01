package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "论文详情API")
@RestController
public class DocumentController {


    @Autowired
    public DocumentsService documentsService;

    @ApiOperation(value = "指定pmid获取论文详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmid", value = "PubMed号")
    })
    @GetMapping("/info/{pmid}")
    public R<DocumentInfoDto> findInfoById(@PathVariable("pmid") String pmid) {
        DocumentInfoDto documentInfoDto = documentsService.findInfoById(pmid);
        if(documentInfoDto == null){
            return R.fail("输入pmid有误，请检查");
        }
        return R.ok(documentInfoDto);
    }
}
