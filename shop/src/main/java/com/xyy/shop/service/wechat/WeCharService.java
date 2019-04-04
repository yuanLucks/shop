package com.xyy.shop.service.wechat;

/**
 * @Author:XieYuanYang
 * @Description:
 * @Date: Created in 00:46 2019/2/20 0020
 */
public class WeCharService {

    private static final String TOKEN = "xyy";
    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /**
     * 获取token
     */
    private static void getTokenByCode(String mpAppId , String mpAppSecret,String code){
        String url = GET_TOKEN_URL.replace("APPID",mpAppId).replace("SECRET",mpAppSecret).replace("CODE",code);

    }
}
