package com.xyy.shop.controller.seller;

import com.xyy.shop.pojo.order.OrderLikeAgrs;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.service.seller.ISellerOrderService;
import com.xyy.shop.utils.OutExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api("商家订单操作")
@RestController
public class SellerOrderController {

    @Autowired
    private ISellerOrderService iSellerOrderService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("seller/order/like")
    @ApiOperation(value = "模糊查询商品并导出excel" , notes = "ordernumber是订单号，dateSatrt开始时间，dateEnd结束时间，proname商品名称，status订单状态")
    public ResponseData queryOrderByLike(@RequestBody @ModelAttribute OrderLikeAgrs orderLikeAgrs, HttpServletResponse response) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("统计表");
        OutExcelUtil.createTitle(workbook,sheet);

        //要导出的数据
        List<Orders> lists = iSellerOrderService.queryOrderByLike(orderLikeAgrs.getOrdernumber(),orderLikeAgrs.getDateSatrt(),orderLikeAgrs.getDateEnd(),orderLikeAgrs.getProname(),orderLikeAgrs.getStatus());


        //设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据行，并且设置单元格数据
        int rowNum=1;

        for(Orders list : lists) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(list.getOrderid());
            row.createCell(1).setCellValue(list.getProname());
            row.createCell(2).setCellValue(String.valueOf(list.getAmount()));
            row.createCell(3).setCellValue(list.getProsum());
            row.createCell(4).setCellValue(list.getPaydate());
            row.createCell(5).setCellValue(list.getMjwords());
            row.createCell(6).setCellValue(list.getMjname());
            row.createCell(7).setCellValue(list.getOrdernumber());
            row.createCell(8).setCellValue(list.getXddate());
            row.createCell(9).setCellValue(list.getSerialnumber());
            row.createCell(10).setCellValue(list.getPostage());
            row.createCell(11).setCellValue(list.getPayment());
            row.createCell(12).setCellValue(list.getSTATUS());
            row.createCell(13).setCellValue(list.getOrderdate());
            row.createCell(14).setCellValue(list.getUid());
            row.createCell(15).setCellValue(list.getProid());
            row.createCell(16).setCellValue(list.getSendid());
            HSSFCell cell = row.createCell(3);
            cell.setCellValue(list.getOrderid());
            cell.setCellStyle(style);
            rowNum++;
        }

        String fileName = "商城订单查询数据.xls";

        //生成excel文件
        OutExcelUtil.buildExcelFile(fileName, workbook);

        //浏览器下载excel
        buildExcelDocument(fileName,workbook,response);


        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！");
        return  responseData;

    }

    @GetMapping("seller/order")
    @ApiOperation("查询全部订单信息！")
    public ResponseData queryAllOrder(){
        List<Orders> list = iSellerOrderService.queryAllOrder();
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",list);
        return responseData;
    }

    @GetMapping("seller/order/{orderid}")
    @ApiOperation("根据订单编号查询 订单信息")
    public ResponseData queryAllOrderByorderid(@PathVariable int orderid){
        Orders orders = iSellerOrderService.queryAllOrderByorderid(orderid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",orders);
        return responseData;
    }

    @PutMapping("seller/order/send")
    @ApiOperation("批量发货")
    public ResponseData updateOrderByNumber(@RequestParam Date orderdate,@RequestParam String[] number){
        System.out.println(orderdate);
        iSellerOrderService.updateOrderSend(orderdate,number);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"发货成功！");
        return responseData;
    }

    @PutMapping("seller/order/return")
    @ApiOperation("批量退货")
    public ResponseData updateOrderReturnByNumber(@RequestParam Date orderdate,@RequestParam String[] number){
        iSellerOrderService.updateOrderReturnByNumber(orderdate,number);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"退货申请成功！");
        return responseData;
    }


    @PostMapping("seller/order/coupon")
    @ApiOperation("添加优惠卷")
    public ResponseData insertCoupon(@ModelAttribute Coupon coupon){
        iSellerOrderService.insertCoupon(coupon);
        System.out.println(coupon.toString());
        //用redis的消息通知 设置优惠卷的过期时间
        redisTemplate.opsForValue().set("coupon" + coupon.getId() , String.valueOf(coupon.getId()) , coupon.getExpiretime().getTime() - coupon.getCreartetime().getTime() , TimeUnit.MILLISECONDS);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"添加优惠卷成功！");
        return responseData;
    }

    /**
     * 浏览器下载excel
     */
    protected void buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        System.out.println();
        workbook.write(outputStream);
        outputStream.flush();
        System.out.println();
        outputStream.close();
    }
}
