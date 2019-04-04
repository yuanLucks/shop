package com.xyy.shop.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户对象")
public class User implements Serializable {
    @ApiModelProperty(value = "编号",dataType = "int")
    private Integer uid;
    @ApiModelProperty(value = "用户名",dataType = "String")
    private String uname;
    @ApiModelProperty(value = "密码",dataType = "String")
    private String upassword;
    @ApiModelProperty(value = "真实姓名",dataType = "String")
    private String utruename;
    @ApiModelProperty(value = "性别",dataType = "char")
    private char usex;

    @ApiModelProperty(value = "电话",dataType = "String")
    private String utemphone;
    @ApiModelProperty(value = "生日",dataType = "Date")
    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    private Date birthday;
    @ApiModelProperty(value = "状态",dataType = "String")
    private String STATUS;
    @ApiModelProperty(value = "状态码",dataType = "int")
    private Integer CODE;
    @ApiModelProperty(value = "头像",dataType = "String")
    private String uimg;
    @ApiModelProperty(value = "地址",dataType = "int")
    private Integer adderid;
    @ApiModelProperty(value = "是否会员",dataType = "char")
    private char vip;
    @ApiModelProperty(value = "积分",dataType = "int")
    private Integer integral;


    private Address address;
}
