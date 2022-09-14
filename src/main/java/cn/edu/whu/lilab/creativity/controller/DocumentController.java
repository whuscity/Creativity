package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "论文详情API")
@RestController
@CrossOrigin()
public class DocumentController {


    @Autowired
    public DocumentsService documentsService;

    @ApiOperation(value = "指定外部id获取论文详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "externalId", value = "目前只有PubMed号")
    })
    @GetMapping("/info/{externalId}")
    public R<DocumentInfoDto> findInfoById(@PathVariable("externalId") String externalId) {
        DocumentInfoDto documentInfoDto = documentsService.findInfoById(externalId);
        if(documentInfoDto == null){
            return R.fail("输入外部id有误，请检查");
        }
        return R.ok(documentInfoDto);
    }
}
