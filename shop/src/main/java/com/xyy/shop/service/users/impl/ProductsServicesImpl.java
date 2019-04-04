package com.xyy.shop.service.users.impl;

import com.xyy.shop.mapper.ProductsMapper;
import com.xyy.shop.pojo.products.*;
import com.xyy.shop.service.users.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品业务逻辑类实现
 */
@Service
@CacheConfig(cacheNames = "ProductsServicesImpl")
public class ProductsServicesImpl implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    /**
     * 查询推荐商品
     * @return 推荐商品集合
     */
    @Cacheable(value = "queryProtj")
    @Override
    public Map<String,Object> queryProtj() {
        return productsMapper.queryProtj();
    }


    /**
     * 查询轮播图商品
     * @return 轮播图商品集合
     */
    @Cacheable(value = "queryProlun")
    @Override
    public Map<String,Object> queryProlun() {
        return productsMapper.queryProlun();
    }

    /**
     * 关键字模糊搜索商品
     * @param proname 商品名称
     * @return  商品集合
     */
    @Cacheable(value = "product",key = "#proname")
    @Override
    public Map<String,Object> queryProductsByProName(String proname) {
        return productsMapper.queryProductsByProName(proname);
    }

    /**
     * 根据商品编号查询商品详情
     * @param proid 商品编号
     * @return 商品详情
     */
    @Override
    @Cacheable(value = "product",key = "#proid")
    public Map<String, Object> queryProductsByProid(int proid) {
        return productsMapper.queryProductsByProid(proid);
    }

    /**
     * 添加收藏商品
     * @param proid  商品编号
     */
    @CachePut(value = "collect")
    @Transactional
    @Override
    public void insertCollectByProid(int proid,int uid) {
        //根据商品编号查询收藏商品表中是否已收藏该商品
        Collect coll = productsMapper.queryCollectByProid(proid);
        //如果为空 则添加该商品 到 收藏商品表中
        if (coll == null) {
            Collect collect = new Collect();
            collect.setProid(proid);
            collect.setUid(uid);
            productsMapper.insertCollect(collect);
        }
    }

    /**
     * 根据商品编号 与 用户编号 查询最近两条评语
     * @param proid 商品编号
     * @return 该商品评语
     */
    @Cacheable(value = "queryProAssessByProidTopTwo",key = "#proid")
    @Override
    public List<ProAssess> queryProAssessByProidTopTwo(int proid) {
        return productsMapper.queryProAssessByProidTopTwo(proid);
    }

    /**
     * 根据商品编号 于 用户编号 查询全部评语
     * @param proid
     * @return
     */
    @Cacheable(value = "queryAllProAssessByproid",key = "#proid")
    @Override
    public List<ProAssess> queryAllProAssessByproid(int proid) {
        return productsMapper.queryAllProAssessByproid(proid);
    }

    /**
     * 查询全部收藏商品
     * @return 商品信息
     */
    @Cacheable(value = "collect")
    @Override
    public Map<String, Object> queryAllCollect() {
        return productsMapper.queryAllCollect();
    }
}
