package com.xyy.shop.pojo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 接口统一返回格式
 */
@Data
@ApiModel(value = "统一响应数据对象")
@Component
public class ResponseData {
    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "消息")
    private String massege;
    @ApiModelProperty(value = "数据对象")
    private Object result;
    @ApiModelProperty(value = "状态")
    private Boolean isSuccess;

    /**
     * 无参构造器
     */
    public ResponseData(){
        super();
    }

    /**
     * 只返回状态，状态码，消息
     * @param success
     * @param code
     * @param massege
     */
    public ResponseData(Boolean success, Integer code, String massege){
        super();
        this.isSuccess=success;
        this.code=code;
        this.massege=massege;
    }

    /**
     * 只返回状态，状态码，数据对象
     * @param success
     * @param code
     * @param result
     */
    public ResponseData(Boolean success, Integer code, Object result){
        super();
        this.isSuccess=success;
        this.code=code;
        this.result=result;
    }

    /**
     * 返回全部信息即状态，状态码，消息，数据对象
     * @param success
     * @param code
     * @param massege
     * @param result
     */
    public ResponseData(Boolean success, Integer code, String massege, Object result){
        super();
        this.isSuccess=success;
        this.code=code;
        this.massege=massege;
        this.result=result;
    }

    public static void main(String[] args) {
        System.out.println(ReturnCode.ACCOUNT_ERROR);

    }
}
