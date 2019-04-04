package com.xyy.shop.pojo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 订单模糊查询接受收参数对象
 */
@Data
@ApiModel("订单模糊查询接受收参数对象")
public class OrderLikeAgrs {

    @ApiModelProperty(value = "订单号")
    private String ordernumber;

    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @ApiModelProperty(value = "开始时间")
    private Date dateSatrt;

    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @ApiModelProperty(value = "最后时间")
    private Date dateEnd;

    @ApiModelProperty(value = "商品名称")
    private String proname;

    @ApiModelProperty(value = "订单状态")
    private int status;
}
