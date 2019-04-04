package com.xyy.shop.utils;


/**
 * 图片上传判断
 */
public class PictureJudgeUtil {

    /**
     * 判断该文件是不是 图片类型
     * @param fileName
     * @return
     */
    public static Boolean isImageFile(String fileName){
        String[] img_type = new String[]{".jpg",".png",".gif",".bmp"};
        if (fileName == null){
            return false;
        }

        //将所有字符串小写
        fileName = fileName.toLowerCase();
        for (String type : img_type){
            if(fileName.endsWith(type)){
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件后缀名
     * @param fileName 文件全名称
     * @return
     */
    public static String getFileType(String fileName){
        if (fileName != null && fileName.indexOf(".") >= 0){
            return fileName.substring(fileName.lastIndexOf("."),fileName.length());
        }
        return "";
    }
}
