package com.xyy.shop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xyy.shop.pojo.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtHelperUtil {
    /**
     * 解析jwt
     */

    public static Claims parseJWT(String jsonWebToken, String base64Security){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }

    /**
     *
     * @param name  用户名
     * @param userId   用户编号
     * @param base64Security  唯一密钥
     * @return
     */
    public static String createJWT(String name,
                                   String base64Security){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("unique_name", name)
                .setIssuer("yuan")
                .setAudience("kehuduan")
                .signWith(signatureAlgorithm, signingKey);

        //设置token过期时间  一天
        long TTLMillis = 86400 ;

        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }

    /**
     *token的生成方法
     * @param user 用户信息
     * @return token
     */
    public static String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getUid()))
                .sign(Algorithm.HMAC256(user.getUpassword()));
        return token;
    }
}
