package com.xyy.shop.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "送货详情类")
public class SongAddress {
    @ApiModelProperty(value = "地址编号")
    private Integer songid;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String market;
    @ApiModelProperty(value = "区")
    private String district;
    @ApiModelProperty(value = "详细地址")
    private String detaadder;
}
