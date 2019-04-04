package com.xyy.shop.controller.seller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyy.shop.pojo.user.Message;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.service.seller.impl.SellerUserServiceImpl;
import com.xyy.shop.utils.HttpUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("商家对用户操作")
@RestController
public class SellerUserController {

    private static Logger log = LoggerFactory.getLogger(SellerUserController.class);

    @Autowired
    private SellerUserServiceImpl sellerUserService;

    @GetMapping("seller/user")
    @ApiOperation(value = "关键字查询用户信息",notes = "utemphone电话号码，uame姓名，isVip是否vip")
    public ResponseData querySellerUser(@RequestParam(required = false) String utemphone ,@RequestParam(required = false) String uame ,@RequestParam(required = true) int isVip){
        List<User> user = sellerUserService.querySellerUser(utemphone,uame,isVip);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",user);
        return responseData;
    }

    @GetMapping("seller/user/costom")
    @ApiOperation(value = "查询客户列表",notes = "用户名、微信昵称、手机号码、会员等级、积分、购买次数。")
    public ResponseData querySellerCostom(){
        Map<String,Object> map = sellerUserService.querySellerCustom();
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",map);
        return responseData;
    }

    @GetMapping("seller/user/detailed")
    @ApiOperation(value = "查询客户列表详情",notes = "基本信息（姓名、昵称、手机号、收货地址）、资产信息（现有优惠卷、可用积分）、交易统计（总订单数、累计消费、累计退款金额")
    public ResponseData querySellerCustomDetailed(){
        Map<String,Object> map = sellerUserService.querySellerCustomDetailed();
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",map);
        return responseData;
    }

    @DeleteMapping("seller/coupone/{uid}")
    @ApiOperation(value = "清空用户优惠卷")
    public ResponseData deleteSellerUcouponeByUid(@RequestParam int uid){
        sellerUserService.deleteSellerUcouponeByUid(uid);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"清空用户优惠卷成功！");
        return responseData;
    }

    @PutMapping("seller/user/{uid}")
    @ApiOperation(value = "清空用户积分")
    public ResponseData updateSellerUserByUid(@RequestParam int uid){
        sellerUserService.updateSellerUserByUid(uid);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"清空用户积分成功！");
        return responseData;
    }

    @PutMapping("seller/vipdetailed")
    @ApiOperation(value = "修改用户等级")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "vipid" ,name = "会员等级"),
            @ApiImplicitParam(value = "uid" ,name = "用户编号")
    })
    public ResponseData updateVipdetailedByVipidAndVip(@RequestParam int uid,@RequestParam int vipid){
        int i = sellerUserService.updateVipdetailed(vipid,uid);
        if (i > 0){
            ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"修改用户等级成功！");
            return responseData;
        }else{
            ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"修改用户等级失败！");
            return responseData;
        }
    }

    @PostMapping("seller/message")
    @ApiOperation(value = "添加留言")
    public ResponseData insertMessage(@RequestBody Message message ,@RequestParam int uid){
        message.setUid(uid);
        System.out.println(message.toString());
        sellerUserService.insertMessage(message);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"添加留言成功！");
        return responseData;
    }

    @GetMapping("seller/message/{uid}")
    @ApiOperation(value = "查询留言")
    public ResponseData selectMessageByUid(@RequestParam int uid){
        List<Message> list = sellerUserService.selectMessageByUid(uid);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询留言成功！" ,list);
        return responseData;
    }

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.grantType}")
    private String grantType;
    // wx.grantType=authorization_code

    @Value("${wx.requestUrl}")
    private String requestUrl;
    // wx.requestUrl=https://api.weixin.qq.com/sns/jscode2session



    @GetMapping("/query/openid")
    @ApiOperation(value = "根据code  获取openid")
    public ResponseData getSession(@RequestParam(required = true) String code) {
        String url = this.requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type="
                + grantType;
        String data = HttpUtil.get(url);
        log.debug("请求地址：{}",  url);
        log.debug("请求结果：{}", data);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> json = null;
            json = mapper.readValue(data, Map.class);
            ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！" ,json);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询失败！" );
        return responseData;
    }

}
