package com.xyy.shop.listener;

import com.xyy.shop.service.seller.ISellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * redis 的监听器
 * 配置redis消息的监听器
 *      获取redis中的消息并进行处理
 */
@Component
public class RedisMessageListener extends KeyExpirationEventMessageListener {

    @Autowired
    private ISellerOrderService iSellerOrderService;

    public RedisMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }



    /**
     * 消息处理
     * @param message  完整的消息（频道的消息，以及消息的具体内容）
     * @param bytes 获取频道的消息
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        int id = Integer.valueOf(new String(message.getBody()).substring(7));
        iSellerOrderService.updateCoupon(1,id);
        System.out.println("从channe为：" + new String(message.getChannel()) + "获取的一条新的消息，失效Key为：" + new String(message.getBody()) + "。");
    }
}
