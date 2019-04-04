package com.xyy.shop.pojo.products;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("优惠卷对象")
public class Coupon {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("优惠卷名称")
    private String name;
    @ApiModelProperty("金额")
    private BigDecimal money;
    @ApiModelProperty("优惠卷说明")
    private String coupondesc;
    @ApiModelProperty("获取时间")
    private Date creartetime;
    @ApiModelProperty("失效时间")
    private Date expiretime;
    @ApiModelProperty("状态")
    private Integer status;
}
