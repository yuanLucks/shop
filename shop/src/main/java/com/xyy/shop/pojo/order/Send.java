package com.xyy.shop.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "送货对象",description = "送货类")
public class Send {
    @ApiModelProperty(value = "送货编号")
    private Integer sendid;
    @ApiModelProperty(value = "送货号码")
    private String temphone;
    @ApiModelProperty(value = "昵称")
    private String NAME;
    @ApiModelProperty(value = "是否默认")
    private char defaults;
    @ApiModelProperty(value = "送货详细地址" , hidden = true)
    private Integer songid;
    @ApiModelProperty(value = "用户编号" , hidden = true)
    private Integer uid;

    @ApiModelProperty(value = "送货详细地址")
    private SongAddress songAddress;
}
