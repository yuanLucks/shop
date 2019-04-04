package com.xyy.shop.service.seller.impl;

import com.xyy.shop.mapper.SellerProductMapper;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.pojo.products.Products;
import com.xyy.shop.pojo.products.ProductsGroup;
import com.xyy.shop.service.seller.ISellerOrderService;
import com.xyy.shop.service.seller.ISellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 商家对商品操作业务逻辑接口实现类
 */
@Service
@CacheConfig(cacheNames = "SellerProductServiceImpl")
public class SellerProductServiceImpl implements ISellerProductService {

    @Autowired
    private SellerProductMapper sellerProductMapper;

    @Autowired
    private ISellerOrderService iSellerOrderService;

    /**
     * 查询当天订单成交量 与 支付订单数量
     */
    @Cacheable(value = "queryTodayOrderAmountAndCount")
    @Override
    public Map<String,Object> queryTodayOrderAmountAndCount() {
       return sellerProductMapper.queryTodayOrderAmountAndCount();
    }

    /**
     * 根据条件模糊查询 商品
     * @param proname 商品名称
     * @param groups    商品分组
     * @param typeid    类型
     * @param startOne  最小价格
     * @param startTwo  最大价格
     * @return 商品详情
     */
    @Cacheable(value = "queryProductByLike")
    @Override
    public Map<String, Object> queryProductByLike(String proname, int groups, String typeid, int startOne, int startTwo) {
        return sellerProductMapper.queryProductByLike(proname,groups,typeid,startOne,startTwo);
    }

    /**
     * 根据商品类别  删除商品
     * @param groups 商品类别
     */
    @Caching(
        evict = {
                @CacheEvict(value = "queryProductByLike"),
                @CacheEvict(value = "queryProtj"),
                @CacheEvict(value = "queryProlun"),
                @CacheEvict(value = "product"),
                @CacheEvict(value = "queryProAssessByProidTopTwo"),
                @CacheEvict(value = "queryAllProAssessByproid"),
                @CacheEvict(value = "collect")
        }

    )
    @Override
    public void deleteProductByGroups(int groups) {
        sellerProductMapper.deleteProductByGroups(groups);
    }

    /**
     * 根据商品编号 删除 商品
     * @param proid 商品id
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "queryProductByLike"),
                    @CacheEvict(value = "queryProtj"),
                    @CacheEvict(value = "queryProlun"),
                    @CacheEvict(value = "product"),
                    @CacheEvict(value = "queryProAssessByProidTopTwo"),
                    @CacheEvict(value = "queryAllProAssessByproid"),
                    @CacheEvict(value = "collect")
            }

    )
    @Override
    public void deleteProductByProid(int proid) {
        sellerProductMapper.deleteProductByProid(proid);
    }

    /**
     * 添加商品类别
     * @param proType 商品类别信息
     */
    @Override
    public void insertProtype(ProType proType) {
        sellerProductMapper.insertProtype(proType);
    }

    /**
     * 删除类别
     * @param typeid
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "queryProductByLike"),
                    @CacheEvict(value = "queryProtj"),
                    @CacheEvict(value = "queryProlun"),
                    @CacheEvict(value = "product"),
                    @CacheEvict(value = "queryProAssessByProidTopTwo"),
                    @CacheEvict(value = "queryAllProAssessByproid"),
                    @CacheEvict(value = "collect")
            }

    )
    @Transactional
    @Override
    public void deleteProtype(int typeid) {
        sellerProductMapper.deleteProductByTypeid(typeid);
        sellerProductMapper.deleteProtype(typeid);
    }

    /**
     * 修改类别
     * @param proType
     */
    @Caching(
            put = {
                    @CachePut(value = "queryProductByLike"),
                    @CachePut(value = "queryProtj"),
                    @CachePut(value = "queryProlun"),
                    @CachePut(value = "product"),
                    @CachePut(value = "queryProAssessByProidTopTwo"),
                    @CachePut(value = "queryAllProAssessByproid"),
                    @CachePut(value = "collect")
            }

    )
    @Override
    public void updateProtype(ProType proType) {
        sellerProductMapper.updateProtype(proType);
    }

    /**
     * 增加商品分组
     * @param productsGroup 商品分组信息
     */
    @Override
    public void insertProductGroups(ProductsGroup productsGroup) {
        sellerProductMapper.insertProductGroups(productsGroup);
    }

    /**
     * 删除商品分组
     * @param groupid 商品分组编号
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "queryProductByLike"),
                    @CacheEvict(value = "queryProtj"),
                    @CacheEvict(value = "queryProlun"),
                    @CacheEvict(value = "product"),
                    @CacheEvict(value = "queryProAssessByProidTopTwo"),
                    @CacheEvict(value = "queryAllProAssessByproid"),
                    @CacheEvict(value = "collect")
            }

    )
    @Transactional
    @Override
    public void deleteProductGroupsByGroupId(int groupid) {
        sellerProductMapper.deleteProductGroupsByGroupId(groupid);
        sellerProductMapper.deleteProductGroupsByGroupId(groupid);
    }

    /**
     * 修改商品分组
     * @param productsGroup 商品分组信息
     */
    @CachePut()
    @Override
    public void updateProductGroups(ProductsGroup productsGroup) {
        sellerProductMapper.updateProductGroups(productsGroup);
    }

    /**
     * 修改商品
     * @param products 商品信息
     */
    @Caching(
            put = {
                    @CachePut(value = "collect"),
                    @CachePut(value = "product"),
                    @CachePut(value = "queryProlun"),
                    @CachePut(value = "queryProtj"),
                    @CachePut(value = "queryProductByLike"),
            }
    )
    @Override
    public void updateProducts(Products products) {
        sellerProductMapper.updateProducts(products);
    }

    /**
     * 发布商品
     * @param products 商品数据
     * @return 受影响行数
     */
    @Caching(
            put = {
                    @CachePut(value = "collect"),
                    @CachePut(value = "product"),
                    @CachePut(value = "queryProlun"),
                    @CachePut(value = "queryProtj"),
                    @CachePut(value = "queryProductByLike"),
            }
    )
    @Override
    public int insertProduct(Products products) {
        int i = sellerProductMapper.insertProduct(products);
        return i;
    }

    /**
     * 添加图片
     * @param products 图片数据
     * @return 插入行数
     */
    @Override
    public int insertPropicture(Products products) {
        int i = sellerProductMapper.insertPropicture(products);
        return i;
    }


}
