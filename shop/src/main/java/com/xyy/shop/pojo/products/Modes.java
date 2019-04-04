package com.xyy.shop.pojo.products;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("配送方式对象")
public class Modes {

    @ApiModelProperty("编号")
    private Integer modesid;
    @ApiModelProperty("配送方式名称")
    private String modelname;
    @ApiModelProperty("店铺编号")
    private Integer storeid;
    @ApiModelProperty("配送费用")
    private Integer price;

}
