package cn.edu.whu.lilab.creativity.controller;

import cn.edu.whu.lilab.creativity.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Api(tags = "测试controller")
public class TestController {


    @GetMapping("/hello/{message}")
    @ApiOperation(value = "测试公共返回对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message",value = "调用状态信息")
    })
    public R<String> test(@PathVariable String message){
        return R.ok("测试成功",message);
    }
}
