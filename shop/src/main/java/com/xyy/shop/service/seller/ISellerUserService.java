package com.xyy.shop.service.seller;

import com.xyy.shop.pojo.user.Message;
import com.xyy.shop.pojo.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商家对用户业务逻辑操作接口类
 */
public interface ISellerUserService {
    /**
     * 关键字搜索用户
     *
     * @param utemphone 电话
     * @param vip       是否会员
     * @return 用户信息
     * @Param uname 姓名
     */
    List<User> querySellerUser(String utemphone, String uname, int vip);

    /**
     * 客户列表
     *
     * @return 客户信息
     */
    Map<String, Object> querySellerCustom();

    /**
     * 客户详情
     *
     * @return 客户详情
     */
    Map<String, Object> querySellerCustomDetailed();

    /**
     * 清空用户优惠卷
     *
     * @param uid 用户编号
     */
    void deleteSellerUcouponeByUid(int uid);

    /**
     * 清空用户积分
     *
     * @param uid 用户编号
     */
    void updateSellerUserByUid(int uid);

    /**
     * 修改用户会员等级
     *
     * @param vipid 会员等级编号
     */
    int updateVipdetailed(int vipid, int uid);

    /**
     * 添加留言
     *
     * @param message 留言详情
     */
    void insertMessage(Message message);

    /**
     * 根据用户编号  查询商家给用户的留言
     *
     * @param uid 用户编号
     * @return 留言详情
     */
    List<Message> selectMessageByUid(int uid);


}