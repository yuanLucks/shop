package com.xyy.shop.pojo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@ApiModel(value = "订单对象",description = "订单类")
public class Orders {
    @ApiModelProperty(value = "订单编号")
    private Integer orderid;
    @ApiModelProperty(value = "商品名称")
    private String proname; 		            //商品名称
    @ApiModelProperty(value = "金额")
    private BigDecimal amount; 		            //金额
    @ApiModelProperty(value = "购买数量")
    private Integer prosum; 			        //购买数量

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    private Date paydate; 		                //支付时间

    @ApiModelProperty(value = "卖家留言")
    private String mjwords; 			        //卖家留言

    @ApiModelProperty(value = "卖家昵称")
    private String mjname; 			            //卖家昵称

    @ApiModelProperty(value = "订单号")
    private String ordernumber;		            //订单号

    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @ApiModelProperty(value = "下单时间")
    private Date xddate;			            //下单时间

    @ApiModelProperty(value = "支付流水号")
    private String serialnumber;	            //支付流水号

    @ApiModelProperty(value = "邮费")
    private String postage;			            //邮费

    @ApiModelProperty(value = "支付方式")
    private String payment;			            //支付方式

    @ApiModelProperty(value = "订单状态")
    private String STATUS;			            //订单状态

    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @ApiModelProperty(value = "订单修改时间")
    private Date orderdate;


    @ApiModelProperty(value = "用户编号")
    private Integer uid;
    @ApiModelProperty(value = "商品编号")
    private Integer proid;
    @ApiModelProperty(value = "地址编号")
    private Integer sendid;

}
