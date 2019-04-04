package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "图片对象",description = "图片实体类")
public class ProPicture {
    @ApiModelProperty(value = "编号")
    private Integer picid;
    @ApiModelProperty(value = "图片路径")
    private String picimg;
    @ApiModelProperty(value = "商品编号")
    private Integer proid;
}
