package com.xyy.shop;

import com.xyy.shop.pojo.wx.WechatAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void contextLoads() {
        WechatAccount wechatAccount = new WechatAccount();
        System.out.println(wechatAccount.getMpAddId());
    }

}

