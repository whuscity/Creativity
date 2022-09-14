package cn.edu.whu.lilab.creativity.controller;


import cn.edu.whu.lilab.creativity.domain.DocumentAbstract;
import cn.edu.whu.lilab.creativity.model.R;
import cn.edu.whu.lilab.creativity.service.DocumentAbstractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "摘要API")
@RestController
@RequestMapping("/abstract")
@CrossOrigin()
public class AbstractController {

    @Autowired
    public DocumentAbstractService documentAbstractService;

    @ApiOperation(value = "指定pmid获取摘要信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmid",value = "PubMed号")
    })
    @GetMapping("/{pmid}")
    public R<DocumentAbstract> findById(@PathVariable String pmid){
        DocumentAbstract abstractInfo = documentAbstractService.findByExternalId(pmid);
        if(abstractInfo == null){
            return R.fail("指定pmid有误");
        }
        return R.ok(abstractInfo);
    }
}
