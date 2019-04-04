package com.xyy.shop.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UuidUtils {
    /**
     * 生成16位唯一的UUID
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineld = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0){
            hashCodeV = - hashCodeV;
        }

        return machineld + String.format("%015d" , hashCodeV);
    }

}
