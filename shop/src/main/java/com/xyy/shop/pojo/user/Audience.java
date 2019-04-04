package com.xyy.shop.pojo.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT  实体类
 */
@Data
@ConfigurationProperties(prefix = "audience")
@Component
@Getter
@Setter
@ToString
public class Audience {

    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;

}
