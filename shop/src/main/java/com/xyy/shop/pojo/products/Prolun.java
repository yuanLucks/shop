package com.xyy.shop.pojo.products;


import com.xyy.shop.pojo.products.Products;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 轮播图   实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "轮播图对象")
public class Prolun {
    @ApiModelProperty(value = "编号")
    private Integer lunid;
    private Integer proid;

    @ApiModelProperty(value = "商品")
    private Products products;
}
