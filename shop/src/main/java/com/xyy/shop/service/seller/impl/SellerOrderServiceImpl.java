package com.xyy.shop.service.seller.impl;

import com.xyy.shop.mapper.SellerOrderMapper;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.service.seller.ISellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商家对订单操作业务逻辑接口实现类
 */
@Service
public class SellerOrderServiceImpl implements ISellerOrderService {

    @Autowired
    private SellerOrderMapper sellerOrderMapper;

    /**
     * 模糊查询订单
     * @param ordernumber 订单号
     * @param dateSatrt 开始时间
     * @param dateEnd   结束时间
     * @param proname   商品名称
     * @param status    订单站状态
     * @return  订单详情
     */
    @Override
    public List<Orders> queryOrderByLike(String ordernumber, Date dateSatrt, Date dateEnd, String proname, int status) {
        return sellerOrderMapper.queryOrderByLike(ordernumber,dateSatrt,dateEnd,proname,status);
    }

    /**
     * 查询全部订单
     * @return 全部订单详情
     */
    @Override
    public List<Orders> queryAllOrder() {
        return sellerOrderMapper.queryAllOrder();
    }

    /**
     * 查询全部订单  根据订单编号
     * @return 单个订单详情
     */
    @Override
    public Orders queryAllOrderByorderid(int orderid) {
        return sellerOrderMapper.queryAllOrderByorderid(orderid);
    }

    /**
     * 批量发货
     * @param orderdate 订单发货时间
     * @param number 订单编号
     */
    @Override
    public void updateOrderSend(Date orderdate,String[] number) {
        //设置订单状态
        int status = 2;
        sellerOrderMapper.updateOrderByNumber(orderdate ,status ,number);
    }

    /**
     * 批量退货
     * @param orderdate 退货时间
     * @param number  订单号
     */
    @Override
    public void updateOrderReturnByNumber(Date orderdate, String[] number) {
        //设置订单状态
        int status = 3;
        sellerOrderMapper.updateOrderReturnByNumber(orderdate,status,number);
    }

    /**
     * 添加优惠卷
     * @param coupon 优惠卷信息
     * @return 影响行数
     */
    @Override
    public int insertCoupon(Coupon coupon) {
        return sellerOrderMapper.insertCoupon(coupon);
    }

    /**
     * 修改优惠卷状态
     * @param status 状态
     * @param id    编号
     * @return 返回类型
     */
    @Override
    public int updateCoupon(int status, int id) {
        return sellerOrderMapper.updateCoupon(status,id);
    }


}
