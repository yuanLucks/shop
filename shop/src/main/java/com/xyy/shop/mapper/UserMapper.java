package com.xyy.shop.mapper;

import com.xyy.shop.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper  //表示 加这个注解才可以映射到mybatis的xml文件中
public interface UserMapper {

    /**
     * 根据用户名密码 登入
     * @param uname 用户名
     * @param upassword 密码
     * @return 用户信息
     */
    User userLoginByUsernameAndPassword(@Param("uname") String uname , @Param("upassword") String upassword);

    /**
     * 注册用户
     * @param user
     */
    void addUser(User user);

    /**
     * 用户查询根据用户名
     * @param uname
     * @return
     */
    User queryUserByuname(String uname);

    /**
     * 修改用户状态
     * @param STATUS
     * @param uid
     */
    void updateUserStatus(@Param("STATUS") String STATUS,@Param("uid") int uid);

    /**
     * 注册用户时添加真实地址
     * @param user 用户信息
     */
    void addAddress(User user);

    /**
     * 根据用户名查询用户编号
     * @param uname 用户名
     * @return  用户编号
     */
    int queryUseridByUname(String uname);

    /**
     * 查询用户根据用户编号
     * @param uid 用户编号
     * @return 用户信息
     */
    User queryUserByUid(int uid);

}
