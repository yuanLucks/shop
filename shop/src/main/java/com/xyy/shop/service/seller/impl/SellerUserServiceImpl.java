package com.xyy.shop.service.seller.impl;

import com.xyy.shop.mapper.SellerUserMapper;
import com.xyy.shop.pojo.user.Message;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.service.seller.ISellerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商家对用户业务逻辑操作接口实现类
 */
@CacheConfig(cacheNames = "SellerUserServiceImpl")
@Service
public class SellerUserServiceImpl implements ISellerUserService {

    @Autowired
    private SellerUserMapper sellerUserMapper;

    /**
     * 关键字搜索用户
     * @param utemphone 电话
     * @Param uname 姓名
     * @param vip   是否会员
     * @return 用户信息
     */

    @Override
    public List<User> querySellerUser(String utemphone, String uname, int vip) {
        return sellerUserMapper.querySellerUser(utemphone,uname,vip);
    }

    /**
     * 客户列表
     * @return 客户列表
     */

    @Override
    public Map<String, Object> querySellerCustom() {
        return sellerUserMapper.querySellerCustom();
    }

    /**
     * 客户详情
     * @return 客户详情
     */
    @Override
    public Map<String, Object> querySellerCustomDetailed() {
        return sellerUserMapper.querySellerCustomDetailed();
    }


    /**
     * 清空用户优惠卷
     * @param uid 用户编号
     */
    @Override
    public void deleteSellerUcouponeByUid(int uid) {
        sellerUserMapper.deleteSellerUcouponeByUid(uid);
    }

    /**
     * 清空用户积分
     * @param uid 用户编号
     */
    @Override
    public void updateSellerUserByUid(int uid) {
        sellerUserMapper.updateSellerUserByUid(uid);
    }

    /**
     * 修改用户会员等级
     * @param vipid 会员等级编号
     */
    @Override
    public int updateVipdetailed(int vipid, int uid) {
        int detid = sellerUserMapper.selectSellerUserByUid(uid);
        int line = sellerUserMapper.updateVipdetailedByVipidAndVip(vipid,detid);
        return line;
    }

    /**
     * 添加留言
     * @param message 留言详情
     */
    @Override
    public void insertMessage(Message message) {
        sellerUserMapper.insertMessage(message);
    }

    /**
     * 根据用户编号  查询商家给用户的留言
     * @param uid 用户编号
     * @return 留言详情
     */
    @Override
    public List<Message> selectMessageByUid(int uid) {
        return sellerUserMapper.selectMessageByUid(uid);
    }


}
