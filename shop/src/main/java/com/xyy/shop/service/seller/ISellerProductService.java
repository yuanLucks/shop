package com.xyy.shop.service.seller;

import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.pojo.products.Products;
import com.xyy.shop.pojo.products.ProductsGroup;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 商家对商品业务逻辑操作接口类
 */
public interface ISellerProductService {

    /**
     * 查询当天订单成交量 与 支付订单数量
     */
    Map<String,Object> queryTodayOrderAmountAndCount();

    /**
     * 根据条件模糊查询 商品
     * @param proname 商品名称
     * @param groups    商品分组
     * @param typeid    类型
     * @param startOne  最小价格
     * @param startTwo  最大价格
     * @return 商品详情
     */
    Map<String,Object> queryProductByLike(@Param("proname") String proname , @Param("groups") int groups , @Param("typeid") String typeid , @Param("startOne") int startOne , @Param("startTwo") int startTwo);

    /**
     * 根据商品类别  删除商品
     * @param groups 商品类别
     */
    void deleteProductByGroups(int groups);

    /**
     * 根据商品编号 删除 商品
     * @param proid 商品id
     */
    void deleteProductByProid(int proid);

    /**
     * 添加商品类别
     * @param proType 商品类别信息
     */
    void insertProtype(ProType proType);

    /**
     * 删除类别
     * @param typeid
     */
    void deleteProtype(int typeid);

    /**
     * 修改类别
     * @param proType
     */
    void updateProtype(ProType proType);

    /**
     * 增加商品分组
     * @param productsGroup 商品分组信息
     */
    void insertProductGroups(ProductsGroup productsGroup);

    /**
     * 删除商品分组
     * @param groupid 商品分组编号
     */
    void deleteProductGroupsByGroupId(int groupid);

    /**
     * 修改商品分组
     * @param productsGroup 商品分组信息
     */
    void updateProductGroups(ProductsGroup productsGroup);

    /**
     * 编辑商品信息
     * @param products
     */
    void updateProducts(Products products);

    /**
     * 发布商品
     * @param products 商品数据
     * @return 受影响行数
     */
    int insertProduct(Products products);

    /**
     * 添加 商品图片
     * @param products
     */
    int insertPropicture(Products products);
}
