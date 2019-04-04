package com.xyy.shop.service.users;

import com.xyy.shop.pojo.products.ProAssess;
import com.xyy.shop.pojo.products.Products;
import com.xyy.shop.pojo.products.Prolun;
import com.xyy.shop.pojo.products.Protj;

import java.util.List;
import java.util.Map;

/**
 * 商品业务逻辑接口类
 */
public interface IProductsService {

    /**
     * 查询推荐商品
     * @return 推荐商品
     */
    Map<String,Object> queryProtj();

    /**
     * 查询轮播图推荐商品
     * @return 轮播图商品集合
     */
    Map<String,Object> queryProlun();

    /**
     * 关键字模糊搜索商品
     * @param proname 商品名称
     * @return  商品集合
     */
    Map<String,Object> queryProductsByProName(String proname);

    /**
     * 根据商品编号查询商品详情
     * @param proid 商品编号
     * @return 商品详情
     */
    Map<String , Object> queryProductsByProid(int proid);

    /**
     * 添加商品至收藏商品表
     * @param proid 商品编号
     * @param uid   用户编号
     */
    void insertCollectByProid(int proid,int uid);

    /**
     * 根据商品编号  查询最近两条评语
     * @param proid 商品编号
     * @return 该商品评语
     */
    List<ProAssess> queryProAssessByProidTopTwo(int proid);

    /**
     * 根据商品编号 查询全部评语
     * @param proid
     * @return
     */
    List<ProAssess> queryAllProAssessByproid(int proid);

    /**
     * 查询全部收藏商品
     * @return 商品信息
     */
    Map<String,Object> queryAllCollect();
}
