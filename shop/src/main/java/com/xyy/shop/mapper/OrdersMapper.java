package com.xyy.shop.mapper;

import com.xyy.shop.pojo.order.OrdStatus;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.order.Send;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.Modes;
import com.xyy.shop.pojo.products.ProType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper  //表示 加这个注解才可以映射到mybatis的xml文件中
public interface OrdersMapper {

    /**
     * 添加订单
     * @param orders 订单数据
     */
    void insertOrders(Orders orders);

    /**
     * 根据用户查询全部送货地址
     * @param uid 用户编号
     * @return 送货地址
     */
    List<Send> queryAllSendByUid(int uid);

    /**
     * 添加送货地址
     * @param send 地址信息
     */
    void addSend(Send send);

    /**
     * 添加送货地址详细信息
     * @param send 地址信息
     */
    void addSongAddress(Send send);

    /**
     * 修改订单状态
     */
    void updateOrderStatus();

    /**
     * 订单支付
     * @param orders 下单信息
     */
    void updateOrderPay(Orders orders);

    /**
     * 多个订单支付
     * @param orders
     */
    void updateManyOrderPay(@Param("orders") Orders orders,@Param("ids") int[] ids);

    /**
     * 根据商品编号查询价格
     * @param orderid
     * @return
     */
    String queryPropriceByOrderid(int orderid);

    /**
     * 查询全部状态
     * @return 状态
     */
    List<OrdStatus> queryAllStatus();

    /**
     * 根据订单号 修改订单状态
     * @param status
     * @param ordernumber
     */
    void updateOrderStatusByOrderid(@Param("status")int status,@Param("ordernumber") String ordernumber);

    /**
     * 根据店铺编号 查询店铺配送方式
     * @param storeid 店铺编号
     * @return 店铺配送信息
     */
    List<Modes> queryStoreSendModes(int storeid);

    /**
     * 根据用户编号 修改用户默认地址
     * @param uid 用户编号
     */
    void updateSendAllIsDefault(int uid);

    /**
     * 根据地址编号  设置默认地址
     * @param sendid 地址编号
     */
    void updateDefaultBySendid(int sendid);

    /**
     * 根据订单状态  查询商品信息
     * @param status 订单状态
     * @return 订单信息
     */
    Map queryOrderByStatus(String status);

    /**
     * 查询用户会员 折扣
     * @param uid
     * @return
     */
    BigDecimal selectOrderDiscount(int uid);

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
     * 查询已失效优惠卷
     * @param uid
     * @return
     */
    List<Coupon> queryCouponUse(int uid);

    /**
     * 查询用户优惠卷 优惠金额
     * @param id
     * @return
     */
    BigDecimal queryCouponMoneyById(int id);

    /**
     * 查询全部类别
     * @return
     */
    List<ProType> selectAllProtype();

}
