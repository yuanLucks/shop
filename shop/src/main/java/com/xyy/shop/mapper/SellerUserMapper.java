package com.xyy.shop.mapper;

import com.xyy.shop.pojo.user.Message;
import com.xyy.shop.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

@Mapper  //表示 加这个注解才可以映射到mybatis的xml文件中
public interface SellerUserMapper {

    /**
     * 关键字搜索用户
     * @param utemphone 电话
     * @Param uname 姓名
     * @param vip   是否会员
     * @return 用户信息
     */
    List<User> querySellerUser(@Param("utemphone") String utemphone ,@Param("uname") String uname, @Param("vip") int vip);

    /**
     * 客户列表
     * @return 客户信息
     */
    Map<String,Object> querySellerCustom();

    /**
     * 客户详情
     * @return 客户详情
     */
    Map<String,Object> querySellerCustomDetailed();

    /**
     * 根据用户编号查询vip外键
     * @return
     */
    int selectSellerUserByUid(int uid);

    /**
     * 清空用户优惠卷
     * @param uid 用户编号
     */
    void deleteSellerUcouponeByUid(int uid);

    /**
     * 清空用户积分
     * @param uid 用户编号
     */
    void updateSellerUserByUid(int uid);

    /**
     * 修改用户会员等级
     * @param vipid 会员等级编号
     */
    int updateVipdetailedByVipidAndVip(@Param("vipid") int vipid ,@Param("detid") int detid);

    /**
     * 添加留言
     * @param message 留言详情
     */
    void insertMessage(@Param("message") Message message);

    /**
     * 根据用户编号  查询商家给用户的留言
     * @param uid 用户编号
     * @return 留言详情
     */
    List<Message> selectMessageByUid(int uid);
}
