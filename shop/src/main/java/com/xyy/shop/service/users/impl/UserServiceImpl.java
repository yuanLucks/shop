package com.xyy.shop.service.users.impl;

import com.xyy.shop.mapper.UserMapper;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.service.users.IUserService;
import com.xyy.shop.utils.AddressNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务逻辑类接口实现类
 */
@Service
@CacheConfig(cacheNames = "UserServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名密码 登入
     * @param uname 用户名
     * @param upassword 密码
     * @return 用户信息
     */
    @Cacheable(value = "user")
    @Override
    public User  userLoginByUsernameAndPassword(String uname, String upassword) {
        User user = userMapper.userLoginByUsernameAndPassword(uname,upassword);
        if (user != null){
            user.setSTATUS("在线");
            user.setCODE(200);
            return user;
        }else{
            User u = new User();
            u.setCODE(404);
            return user;
        }
    }

    /**
     * 添加用户
     * @param user 用户数据
     */
    @Transactional
    @Override
    public void regUser(User user) {
        //设置地址编号
        int i = AddressNumberUtil.getAddressNumber();
        user.getAddress().setAdderid(i);
        user.setAdderid(i);
        //添加用户真实地址
        userMapper.addAddress(user);
        //添加用户基本信息
        userMapper.addUser(user);
    }

    /**
     * 根据用户名查询用户名是否存在
     * @param uname
     * @return 是否存在用户名
     */
    @Cacheable(value = "queryUserByuname",key = "#uname")
    @Override
    public boolean queryUserByuname(String uname) {
        User user = userMapper.queryUserByuname(uname);
        if(user != null){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 修改用户状态
     * @param STATUS
     * @param uid
     */
    @CachePut(value = "user")
    @Override
    public void updateUserStatus(String STATUS, int uid) {
        if(STATUS != "" && STATUS != null){
            userMapper.updateUserStatus(STATUS,uid);
        }else{
            STATUS = "下线";
            userMapper.updateUserStatus(STATUS , uid);
        }
    }

    /**
     * 根据用户名查询用户编号
     * @param uname 用户名
     * @return  用户编号
     */
    @Cacheable(value = "queryUseridByUname",key = "#uname")
    @Override
    public int queryUseridByUname(String uname) {
        int uid = userMapper.queryUseridByUname(uname);
        return uid;
    }


    /**
     * 根据用户编号  查询用户
     * @param uid 用户编号
     * @return 用户信息
     */
    @Cacheable(value = "queryUserByUid",key = "#uid")
    @Override
    public User queryUserByUid(int uid) {
        return userMapper.queryUserByUid(uid);
    }


}
