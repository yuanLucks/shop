package com.xyy.shop.mapper;

import com.xyy.shop.pojo.products.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductsMapper {

    /**
     * 查询推全部荐商品
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
     * 添加收藏商品
     * @param collect  收藏商品信息
     */
    void insertCollect(Collect collect);

    /**
     * 根据商品编号查询收藏商品
     * @param proid 商品编号
     * @return 收藏商品详情
     */
    Collect queryCollectByProid(int proid);

    /**
     * 根据商品编号 与 用户编号 查询最近两条评语
     * @param proid 商品编号
     * @return 该商品评语
     */
    List<ProAssess> queryProAssessByProidTopTwo(@Param("proid") int proid);

    /**
     * 根据商品编号 于 用户编号 查询全部评语
     * @param proid
     * @return
     */
    List<ProAssess> queryAllProAssessByproid(@Param("proid") int proid);

    /**
     * 查询全部收藏商品
     * @return 商品信息
     */
    Map<String,Object> queryAllCollect();

}
