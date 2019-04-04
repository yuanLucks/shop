package com.xyy.shop.utils;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author:XieYuanYang
 * @Description: 导入导出excel工具类
 * @Date: Created in 16:39 2019/3/7 0007
 */
public class OutExcelUtil {

    /**
     * 生成Excel
     * @param filename
     * @param workbook
     * @throws IOException
     */
public static void buildExcelFile(String filename , HSSFWorkbook workbook) throws IOException {
    FileOutputStream fos = new FileOutputStream(filename);
    workbook.write(fos);
    fos.flush();
    fos.close();
}

    /**
     * 创建表头
     * @param workbook
     * @param sheet
     */
    public static void createTitle(HSSFWorkbook workbook, HSSFSheet sheet){
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(3,17*256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("订单编号");
        cell.setCellStyle(style);


        cell = row.createCell(1);
        cell.setCellValue("商品编号");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("金额");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("购买数量");
        cell.setCellStyle(style);


        cell = row.createCell(4);
        cell.setCellValue("支付时间");
        cell.setCellStyle(style);


        cell = row.createCell(5);
        cell.setCellValue("卖家留言");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("卖家昵称");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("订单号");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("下单时间");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("支付流水号");
        cell.setCellStyle(style);

        cell = row.createCell(10);
        cell.setCellValue("邮费");
        cell.setCellStyle(style);

        cell = row.createCell(11);
        cell.setCellValue("支付方式");
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue("订单状态");
        cell.setCellStyle(style);

        cell = row.createCell(13);
        cell.setCellValue("订单修改时间");
        cell.setCellStyle(style);

        cell = row.createCell(14);
        cell.setCellValue("用户编号");
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue("商品编号");
        cell.setCellStyle(style);

        cell = row.createCell(16);
        cell.setCellValue("地址编号");
        cell.setCellStyle(style);

    }
}
