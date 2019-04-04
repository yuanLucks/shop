package com.xyy.shop.service.users.impl;

import com.xyy.shop.mapper.OrdersMapper;
import com.xyy.shop.mapper.ProductsMapper;
import com.xyy.shop.pojo.order.OrdStatus;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.order.Send;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.Modes;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.service.users.IOrdersService;
import com.xyy.shop.utils.PaySerialNumberUtil;
import com.xyy.shop.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单业务逻辑类接口实现
 */
@Service
@CacheConfig(cacheNames="OrdersServiceImpl")
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductsMapper productsMapper;

    /**
     * 添加订单
     * @param orders 订单数据
     */
    @Override
    public void insertOrders(Orders orders,int uid,String couponId) {
        //设置下单时间
        orders.setXddate(new Date());
        //设置订单修改时间
        orders.setOrderdate(new Date());

        //设置商品名称  与  邮费
        Map<String , Object> mapProducts = productsMapper.queryProductsByProid(orders.getProid());
        for (Map.Entry<String, Object> entry : mapProducts.entrySet()) {
            //商品名称
            if (entry.getKey() == "proname") {
                orders.setProname((String) entry.getValue());
            }
            //邮费
            if (entry.getKey() == "profreight") {
                orders.setPostage((String) entry.getValue());
            }
        }

        //设置总金额
        //查询折扣
        BigDecimal paySum;
        BigDecimal discount1 = ordersMapper.selectOrderDiscount(uid);
        if (discount1 != null){
            //获取商品原价
            BigDecimal discount2 = orders.getAmount();
            //会员 折扣价  除法
             paySum = discount1.divide(discount2, 2, RoundingMode.HALF_UP);
             orders.setAmount(paySum);
            //判断是否有优惠卷
            if (couponId != null){
                BigDecimal money = ordersMapper.queryCouponMoneyById(Integer.valueOf(couponId));
                paySum = paySum.subtract(money);
                //设置总金额
                orders.setAmount(paySum);
            }
        }


        //设置订单状态
        orders.setSTATUS("买家下单");

        //设置订单号
        orders.setOrdernumber(UuidUtils.getOrderIdByUUId());

        ordersMapper.insertOrders(orders);
    }

    /**
     * 根据用户查询全部送货地址
     * @param uid 用户编号
     * @return 送货地址
     */
    @Cacheable(value = "queryAllSendByUid",key = "#uid")
    @Override
    public List<Send> queryAllSendByUid(int uid) {
        return ordersMapper.queryAllSendByUid(uid);
    }

    /**
     *添加送货地址
     * @param send 地址信息
     */
    @Transactional
    @Override
    public void addSendAddress(Send send) {
        ordersMapper.addSongAddress(send);
        ordersMapper.addSend(send);
    }


    /**
     * 订单支付
     * @param orders 支付方式
     */
    @Override
    public void OrderPay(Orders orders) {
        //设置订单状态
        orders.setSTATUS("待发货");
        //设置支付流水号
        orders.setSerialnumber(PaySerialNumberUtil.getPaySerialNumber());
        //设置付款时间
        orders.setXddate(new Date());
        ordersMapper.updateOrderPay(orders);
    }

    /**
     * 根据商品编号查询价格
     * @param orderid
     * @return
     */
    @Override
    public String queryPropriceByOrderid(int orderid) {
        return ordersMapper.queryPropriceByOrderid(orderid);
    }

    /**
     * 多个订单支付
     * @param orders
     */
    @Override
    public void updateManyOrderPay(Orders orders, int[] ids) {
        //设置订单修改时间
        orders.setOrderdate(new Date());
        //设置订单状态
        orders.setSTATUS("待发货");
        //设置支付流水号
        orders.setSerialnumber(PaySerialNumberUtil.getPaySerialNumber());
        //设置付款时间
        orders.setXddate(new Date());
        ordersMapper.updateManyOrderPay(orders,ids);
    }

    /**
     * 查询全部状态
     * @return 状态
     */
    @Cacheable(value = "queryAllStatus")
    @Override
    public List<OrdStatus> queryAllStatus() {
        List<OrdStatus> list = ordersMapper.queryAllStatus();
        System.out.println(list.toString() + "dasdasdasdas");
        return list;
    }

    /**
     * 根据订单编 修改订单状态
     * @param status
     * @param ordernumber
     */
    @Override
    public void updateOrderStatusByOrderid(int status, String ordernumber) {

        ordersMapper.updateOrderStatusByOrderid(status,ordernumber);
    }

    /**
     * 根据店铺编号 查询店铺配送方式
     * @param storeid 店铺编号
     * @return 店铺配送信息
     */
    @Cacheable(value = "queryStoreSendModes",key = "storeid")
    @Override
    public List<Modes> queryStoreSendModes(int storeid) {
        return ordersMapper.queryStoreSendModes(storeid);
    }

    /**
     * 设置默认地址
     * @param uid 用户编号
     * @param sendid  地址编号
     */
    @Transactional
    @Override
    public void updateSendDefault(int uid, int sendid) {
        ordersMapper.updateSendAllIsDefault(uid);
        ordersMapper.updateDefaultBySendid(sendid);
    }


    /**
     * 根据订单状态  查询商品信息
     * @param status 订单状态
     * @return 订单信息
     */
    @Cacheable(value = "queryOrderByStatus",key = "#status")
    @Override
    public Map queryOrderByStatus(String status) {
        return ordersMapper.queryOrderByStatus(status);
    }

    /**
     * 查询有效优惠卷
     * @param uid
     * @return
     */
    @Cacheable(value = "queryCouponValid" ,key = "#uid")
    @Override
    public List<Coupon> queryCouponValid(int uid) {
        return ordersMapper.queryCouponValid(uid);
    }

    /**
     * 查询无效优惠卷
     * @param uid
     * @return
     */
    @Cacheable(value = "queryCouponNo" ,key = "#uid")
    @Override
    public List<Coupon> queryCouponNo(int uid) {
        return ordersMapper.queryCouponNo(uid);
    }

    /**
     * 查询已使用优惠卷
     * @param uid
     * @return
     */
    @Cacheable(value = "queryCouponUse" ,key = "#uid")
    @Override
    public List<Coupon> queryCouponUse(int uid) {
        return ordersMapper.queryCouponUse(uid);
    }

    /**
     * 查询全部类别
     * @return
     */
    @Cacheable(value = "selectAllProtype")
    @Override
    public List<ProType> selectAllProtype() {
        return ordersMapper.selectAllProtype();
    }

}
