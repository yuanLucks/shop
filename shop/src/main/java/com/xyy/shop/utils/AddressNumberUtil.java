package com.xyy.shop.utils;

import java.util.Random;

/**
 * 随机获取7位数的数字
 */
public class AddressNumberUtil {

    /**
     * 随机获取7位数的数字
     */
    public static int getAddressNumber() {

        int flag = new Random().nextInt(9999999);
        if (flag < 1000000) {
            flag += 1000000;
        }
        return flag;
    }


    public static void main(String[] args) {
        getAddressNumber();
    }
}
