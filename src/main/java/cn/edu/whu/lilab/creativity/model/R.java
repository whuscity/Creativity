package cn.edu.whu.lilab.creativity.model;

import cn.edu.whu.lilab.creativity.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 封装公共的返回对象
 * @param <T>
 */

@Data
@ApiModel(description = "公共返回对象")
public class R<T>  {

    private static final Integer SUCCESS  = Constants.SUCCESS;
    private static final Integer Fail  = Constants.FAIL;

    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "附加信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> R<T> ok(){
        return responseResult(null, SUCCESS,null);
    }
    public static <T> R<T> ok(T data){
        return responseResult(data,SUCCESS,null);
    }
    public static <T> R<T> ok(T data,String message){
        return responseResult(data,SUCCESS,message);
    }

    public static <T> R<T> fail(){
        return responseResult(null, Fail,null);
    }
    public static <T> R<T> fail(T data){
        return responseResult(data,Fail,null);
    }
    public static <T> R<T> fail(T data,String message){
        return responseResult(data,Fail,message);
    }
    public static <T> R<T> fail(String message){
        return responseResult(null, Fail,message);
    }
    public static <T> R<T> fail(Integer code ,String message){
        return responseResult(null,code,message);
    }


    private static  <T> R<T>  responseResult(T data, Integer code , String message){
        R<T> apiResult = new R<>();
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }


}

