package com.xyy.shop.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单状态对象")
public class OrdStatus {
    @ApiModelProperty("状态编号")
    private Integer STATUS;
    @ApiModelProperty("订单状态")
    private String statusName;

}
