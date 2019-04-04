package com.xyy.shop.service.seller;

import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.products.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商家对订单业务逻辑操作接口类
 */
public interface ISellerOrderService {

    /**
     * 模糊查询订单
     * @param ordernumber 订单号
     * @param dateSatrt 开始时间
     * @param dateEnd   结束时间
     * @param proname   商品名称
     * @param status    订单站状态
     * @return  订单详情
     */
    List<Orders> queryOrderByLike(String ordernumber, Date dateSatrt, Date dateEnd, String proname, int status);

    /**
     * 查询全部订单
     * @return 全部订单详情
     */
    List<Orders> queryAllOrder();

    /**
     * 查询全部订单  根据订单编号
     * @return 单个订单详情
     */
    Orders queryAllOrderByorderid(int orderid);

    /**
     * 批量发货
     * @param orderdate 订单发货时间
     * @param number 订单编号
     */
    void updateOrderSend(Date orderdate, String[] number);

    /**
     * 批量退货
     * @param orderdate 退货时间
     * @param number  订单号
     */
    void updateOrderReturnByNumber(Date orderdate,String[] number);

    /**
     * 添加优惠卷
     * @param coupon 优惠卷信息
     * @return 影响行数
     */
    int insertCoupon(Coupon coupon);

    /**
     * 修改优惠卷状态
     * @param status 状态
     * @param id    编号
     * @return 返回类型
     */
    int updateCoupon(int status , int id);

}
