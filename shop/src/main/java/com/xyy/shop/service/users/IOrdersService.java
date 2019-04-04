package com.xyy.shop.service.users;

import com.xyy.shop.pojo.order.OrdStatus;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.order.Send;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.Modes;
import com.xyy.shop.pojo.products.ProType;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单业务逻辑类接口
 */
public interface IOrdersService {
    /**
     * 添加订单
     * @param orders 订单数据
     */
    void insertOrders(Orders orders ,int uid,String couponId);

    /**
     * 根据用户查询全部送货地址
     * @param uid 用户编号
     * @return 送货地址
     */
    List<Send> queryAllSendByUid(int uid);

    /**
     *添加送货地址
     * @param send 地址信息
     */
    void addSendAddress(Send send);

    /**
     * 订单支付
     * @param orders 订单信息
     */
    void OrderPay(Orders orders);

    /**
     * 根据商品编号查询价格
     * @param orderid
     * @return
     */
    String queryPropriceByOrderid(int orderid);

    /**
     * 多个订单支付
     * @param orders
     */
    void updateManyOrderPay(@Param("orders") Orders orders, @Param("ids") int[] ids);

    /**
     * 查询全部状态
     * @return 状态
     */
    List<OrdStatus> queryAllStatus();

    /**
     * 根据订单编 修改订单状态
     * @param status
     * @param ordernumber
     */
    void updateOrderStatusByOrderid(int status,String ordernumber);

    /**
     * 根据店铺编号 查询店铺配送方式
     * @param storeid 店铺编号
     * @return 店铺配送信息
     */
    List<Modes> queryStoreSendModes(int storeid);

    /**
     * 设置默认地址
     * @param uid 用户编号
     * @param sendid  地址编号
     */
    void updateSendDefault(int uid , int sendid);


    /**
     * 根据订单状态  查询商品信息
     * @param status 订单状态
     * @return 订单信息
     */
    Map queryOrderByStatus(String status);

    /**
     * 查询有效优惠卷
     * @param uid
     * @return
     */
    List<Coupon> queryCouponValid(int uid);

    /**
     * 查询无效优惠卷
     * @param uid
     * @return
     */
    List<Coupon> queryCouponNo(int uid);

    /**
     * 查询已使用优惠卷
     * @param uid
     * @return
     */
    List<Coupon> queryCouponUse(int uid);

    /**
     * 查询全部类别
     * @return
     */
    List<ProType> selectAllProtype();

}
