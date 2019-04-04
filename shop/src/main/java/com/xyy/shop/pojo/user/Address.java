package com.xyy.shop.pojo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel(value = "用户地址对象",description = "地址类")
public class Address implements Serializable {
    @ApiModelProperty(value = "地址编号")
    private Integer adderid;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String market;
    @ApiModelProperty(value = "区")
    private String district;
    @ApiModelProperty(value = "详细地址")
    private String detaadder;

}
