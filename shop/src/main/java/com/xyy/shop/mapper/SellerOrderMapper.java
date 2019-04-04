package com.xyy.shop.mapper;

import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.products.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper  //表示 加这个注解才可以映射到mybatis的xml文件中
public interface SellerOrderMapper {

    /**
     * 模糊查询订单
     * @param ordernumber 订单号
     * @param dateSatrt 开始时间
     * @param dateEnd   结束时间
     * @param proname   商品名称
     * @param status    订单站状态
     * @return  订单详情
     */
    List<Orders> queryOrderByLike(@Param("ordernumber") String ordernumber, @Param("dateSatrt") Date dateSatrt, @Param("dateEnd") Date dateEnd, @Param("proname") String proname, @Param("STATUS") int status);

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
     * @param status 订单状态
     * @param number 订单编号
     */
    void updateOrderByNumber(@Param("orderdate")Date orderdate,@Param("status")int status,@Param("number") String[] number);

    /**
     * 批量退货
     * @param orderdate 退货时间
     * @param status 状态
     * @param number  订单号
     */
    void updateOrderReturnByNumber(@Param("orderdate")Date orderdate,@Param("status")int status,@Param("number") String[] number);

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
