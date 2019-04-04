package com.xyy.shop.service.users;

import com.xyy.shop.pojo.user.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户业务逻辑操作接口类
 */
public interface IUserService {

    /**
     * 根据用户名密码 登入
     * @param uname 用户名
     * @param upassword 密码
     * @return 用户信息
     */
    public abstract User userLoginByUsernameAndPassword(String uname, String upassword);

    /**
     * 注册用户
     * @param user
     */
    public abstract void regUser(User user);

    /**
     * 查询用户名是否存在
     * @param uname 用户名
     * @return boolean
     */
    public abstract boolean queryUserByuname(String uname);

    /**
     * 修改用户状态
     * @param STATUS
     * @param uid
     */
    public abstract void updateUserStatus(@Param("STATUS") String STATUS, @Param("uid") int uid);

    /**
     * 根据用户名查询用户编号
     * @param uname 用户名
     * @return  用户编号
     */
    public abstract int queryUseridByUname(String uname);

    /**
     * 查询用户根据用户编号
     * @param uid 用户编号
     * @return 用户信息
     */
    public abstract User queryUserByUid(int uid);

}
