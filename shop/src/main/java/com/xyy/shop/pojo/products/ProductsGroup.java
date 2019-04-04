package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商品分组类")
public class ProductsGroup {
    @ApiModelProperty(value = "编号")
    private Integer groupid;
    @ApiModelProperty("商品分组名称")
    private String groname;
}
