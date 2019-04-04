package com.xyy.shop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成唯一的支付流水号
 */
public class PaySerialNumberUtil {
    /**
     * 前六位数(20150826)是年月日格式化:yyyyMMdd
     *中间的8位数(00001000)是:00001000,固定4个0+1000
     *在后两位(04):随机生成一个两位数
     *在后两位(00):又是固定的两个0
     *接下来的6位数是(617496):时分秒的格式化HHmmss
     *最后两位是(94):又是随机生成
     * @return 数字
     */
    public static String getPaySerialNumber(){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        String string = date+"00001000"+getTwo()+"00"+seconds+getTwo();
        return string;
    }

    /**
     * 生成两位数的随机数
     * @return  数字
     */
    public static String getTwo(){
        Random rad = new Random();
        String result = rad.nextInt(100) + "";

        if(result.length()==1){
            result = "0" + result;
        }
        return result;
    }
}
