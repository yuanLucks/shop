package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * 这是商品类型实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "商品类型对象")
public class ProType {

    @ApiModelProperty("商品类型编号")
    @Id
    private Integer typeid;
    @ApiModelProperty("商品类型名称")
    private String typename;
}
