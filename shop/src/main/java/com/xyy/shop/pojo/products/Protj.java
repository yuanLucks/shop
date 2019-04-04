package com.xyy.shop.pojo.products;

import com.xyy.shop.pojo.products.Products;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品推荐实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "推荐商品对象")
public class Protj {
    @ApiModelProperty(value = "编号")
    private Integer tjid;
    @ApiModelProperty(value = "外键")
    private Integer proid;

    @ApiModelProperty(value = "商品字段")
    private Products products;
}
