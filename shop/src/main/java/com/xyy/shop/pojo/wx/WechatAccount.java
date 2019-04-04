package com.xyy.shop.pojo.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:XieYuanYang
 * @Description:  微信账户信息
 * @Date: Created in 00:29 2019/2/20 0020
 */
@Data
@Component
public class WechatAccount {

    private String mpAddId = "wxfcf12446d226e32d";
    private String mpAppSecret= "0d59de8f03d7a099f034e8fa3a3bff8e";

}
