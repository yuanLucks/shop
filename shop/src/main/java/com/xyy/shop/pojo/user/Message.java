package com.xyy.shop.pojo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel("用户留言")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @ApiModelProperty(value = "编号")
    private Integer mesId;
    @ApiModelProperty(value = "留言标题")
    private String mesTitle;
    @ApiModelProperty(value = "留言内容")
    private String mesName;
    @ApiModelProperty(value = "用户编号")
    private Integer uid;
}
