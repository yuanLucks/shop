package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 店铺实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "商店对象")
public class Store {
    private Integer storeid;            //店铺编号
    private String storename;           //店铺名称
    private String storesign;           //店铺标志

}
