package com.xyy.shop.pojo.products;

import com.xyy.shop.pojo.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "收藏商品对象")
public class Collect {
    private Integer collid;
    private Integer uid;
    private Integer proid;

    private User user;

    private Products products;
}
