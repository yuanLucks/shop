package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 商品实体类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "商品对象",description = "商品类")
public class Products {


    @ApiModelProperty(value = "商品编号" ,name = "proid",dataType = "int型")
    private Integer proid;
    @ApiModelProperty(value = "商品名称" ,name = "proname",dataType = "String型")
    private String proname;
    @ApiModelProperty(value = "商品现价" ,name = "proprice",dataType = "BigDecimal")
    private BigDecimal proprice;
    @ApiModelProperty(value = "商品原价" ,name = "proyuan",dataType = "BigDecimal")
    private BigDecimal proyuan;
    @ApiModelProperty(value = "商品已买数量" ,name = "probuysum",dataType = "String型")
    private Integer probuysum;              //商品已买数目
    @ApiModelProperty(value = "商品运费" ,name = "profreight",dataType = "String型")
    private String profreight;              //商品运费
    @ApiModelProperty(value = "商品描述" ,name = "prodetailed",dataType = "String型")
    private String prodetailed;
    @ApiModelProperty(value = "商品是否下架" ,name = "proxj",dataType = "String型")
    private char proxj;

    @ApiModelProperty(value = "是否优先显示" ,name = "priority",dataType = "char型")
    private char priority;
    @ApiModelProperty(value = "优先显示" ,name = "firstshow",dataType = "char型")
    private char firstshow;
    @ApiModelProperty(value = "富文本编辑" ,name = "richtext",dataType = "String型")
    private String richtext;
    @ApiModelProperty(value = "会员折扣" ,name = "vipuser",dataType = "BigDecimal型")
    private BigDecimal vipuser;

    private Integer groupid;                //商品分组---外键
    private Integer typeid;                 //商品类型---外键
    private Integer storeid;                //商品店铺---外键
    private Integer coupon;                 //商品优惠卷---外键


    //商品类型
    private ProType proType;

    //店铺
    private Store store;


}
