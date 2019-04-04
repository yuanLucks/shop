package com.xyy.shop.controller.users;

import com.xyy.shop.annotates.UserLoginToken;
import com.xyy.shop.pojo.order.OrdStatus;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.Modes;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.order.Send;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.service.users.IOrdersService;
import com.xyy.shop.service.users.IUserService;
import com.xyy.shop.utils.OutExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@UserLoginToken
@RestController
@Api(value = "订单操作")
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private IUserService iUserService;

    @PostMapping("order/{uid}")
    @ApiOperation(value = "添加订单",notes = "我需要商品编号，商品总金额，商品数量，买家留言，支付方式，送货地址编号")
    public ResponseData addOrder(@RequestBody @ModelAttribute Orders orders,@RequestParam String token ,@RequestParam(required = false) String uid,@RequestParam(value = "couponId",required = false) String couponId){
            iOrdersService.insertOrders(orders,Integer.parseInt(uid),couponId);
            ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"添加订单成功！");
            return responseData;
    }

    @UserLoginToken
    @GetMapping("order/send")
    @ApiOperation("查询每个用户的全部送货地址")
    public ResponseData showSendByUid(@RequestParam int uid){
        List<Send> list = iOrdersService.queryAllSendByUid(uid);

        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),list);
        return responseData;
    }

    @PostMapping("order/send")
    @ApiOperation(value = "添加用户收货地址")
    public ResponseData addSendAddress(@RequestBody @ModelAttribute Send send ){
        System.out.println(send.toString());
        iOrdersService.addSendAddress(send);

        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"添加收货地址成功！");
        return responseData;
    }

    @PutMapping("order")
    @ApiOperation(value = "订单支付单个商品" ,notes = "订单支付，立即付款操作需要 商品编号 和 支付方式 ")
    @ApiImplicitParams({@ApiImplicitParam(name = "payment",value = "支付方式",dataType = "String",paramType = "form"),
                        @ApiImplicitParam(name = "orderid",value = "订单编号",dataType = "int",paramType = "form"),
                        @ApiImplicitParam(name = "payprice",value = "支付金额",dataType = "String",paramType = "form")
                })
    public ResponseData orderPay(@RequestParam String payment ,@RequestParam int orderid,@RequestParam int payprice){
        //查询该商品现价金额
        String price = iOrdersService.queryPropriceByOrderid(orderid);

        //确认订单金额是否正确
        if (price.equals(payprice)){
            Orders orders = new Orders();
            orders.setPayment(payment);
            orders.setOrderid(orderid);
            iOrdersService.OrderPay(orders);

            //设置响应数据
            ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"订单支付成功！");
            return responseData;
        }

        //设置响应数据
        ResponseData responseData = new ResponseData(true,ReturnCode.AMOUNT_NOT_QUERY.getCode(),"订单支付失败,订单金额错误！");
        return responseData;
    }


    @PutMapping("order/{sumprice}")
    @ApiOperation(value = "购物车支付订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "str",value = "多个订单编号"),
            @ApiImplicitParam(name = "payment",value = "支付方式")})
    public ResponseData manyOrderPay(@RequestBody @RequestParam int[] str , @RequestParam String payment,@PathVariable String sumprice){
            Orders orders = new Orders();
            orders.setPayment(payment);
            iOrdersService.updateManyOrderPay(orders,str);

            //设置响应数据
            ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"订单支付成功！");
            return responseData;
    }

    @GetMapping("order/status")
    @ApiOperation("查询订单全部状态")
    public ResponseData queryOrderStatus(){
        List<OrdStatus> list = iOrdersService.queryAllStatus();
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",list);
        return responseData;
    }

    @PutMapping("order/status")
    @ApiOperation("修改订单状态")
    public ResponseData updateOrderStatusByOrderid(@RequestParam String ordernumber,@RequestParam int status){
        iOrdersService.updateOrderStatusByOrderid(status,ordernumber);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"修改订单状态成功！");
        return responseData;
    }


    @UserLoginToken
    @GetMapping("order/store/{storeid}")
    @ApiOperation("查询店铺配送方式！")
    public ResponseData queryStoreSendModes(@PathVariable int storeid){
        List<Modes> list = iOrdersService.queryStoreSendModes(storeid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",list);
        return  responseData;
    }

    @UserLoginToken
    @GetMapping("order/send/{sendid}")
    @ApiOperation("设置默认地址")
    public ResponseData updateSendDefault( @PathVariable int sendid,@RequestParam int uid){

        iOrdersService.updateSendDefault(uid,sendid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"设置默认地址成功！");
        return responseData;
    }

    @GetMapping("order")
    @ApiOperation("根据订单状态查询 全部订单")
    public ResponseData queryOrderByStatus(@RequestParam String status){
        Map map = iOrdersService.queryOrderByStatus(status);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",map);
        return  responseData;
    }

    @GetMapping("order/coupon/{uid}")
    @ApiOperation(value = "查询用户优惠卷" , notes = "注意，temp=0获取有效优惠卷，temp=1获取无效优惠卷，temp=2获取使用过的优惠卷")
    public ResponseData queryCouponValid(@PathVariable int uid , @RequestParam String temp){
        if (temp.equals(String.valueOf(0))){
            //有效优惠卷
            List<Coupon> listCoupon = iOrdersService.queryCouponValid(uid);
            ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",listCoupon);
            return  responseData;
        }else if (temp.equals(String.valueOf(1))){
            //无效优惠卷
            List<Coupon> listCoupon = iOrdersService.queryCouponNo(uid);
            ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",listCoupon);
            return  responseData;
        }else  if (temp.equals(String.valueOf(2))){
            //使用过的优惠卷
            List<Coupon> listCoupon = iOrdersService.queryCouponUse(uid);
            ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",listCoupon);
            return  responseData;
        }
        return null;
    }

    @GetMapping("order/query/protype")
    @ApiOperation(value = "查询全部商品类别")
    public ResponseData selectAllProtype(@PathVariable int uid , @RequestParam String temp){
        List<ProType> listProtype = iOrdersService.selectAllProtype();
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",listProtype);
        return  responseData;
    }

}
