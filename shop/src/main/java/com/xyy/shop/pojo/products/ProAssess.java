package com.xyy.shop.pojo.products;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@ApiModel(value = "商品评价对象")
public class ProAssess implements Serializable {
    private Integer assid;
    @ApiModelProperty(value = "商品评价" ,name = "assess",dataType = "String")
    private String assess;
    @ApiModelProperty(value = "评价时间" ,name = "assessdate",dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date assessdate;
    @ApiModelProperty(value = "星评1-5" ,name = "gather",dataType = "int型")
    private Integer gather;

    private Integer uid;
    private Integer proid;

}
