package com.xyy.shop.controller.users;

import com.xyy.shop.annotates.PassToken;
import com.xyy.shop.annotates.UserLoginToken;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.pojo.wx.WechatAccount;
import com.xyy.shop.service.users.IUserService;
import com.xyy.shop.utils.JwtHelperUtil;
import com.xyy.shop.utils.PictureJudgeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(value = "用户操作")
public class UserController {

    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    @Autowired
    private IUserService iUserService;

    @Autowired
    private WechatAccount wechatAccount;

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/auth")
    @ApiOperation(value = "根据code获取openid",notes = "返回的是获取access_tokemn的路径")
    public ResponseData getCode(@RequestParam("code") String code,@RequestParam("state") String state){
        //String url = GET_TOKEN_URL.replace("APPID",wechatAccount.getMpAddId()).replace("SECRET",wechatAccount.getMpAppSecret()).replace("CODE",code);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        ResponseData responseData = new ResponseData(false, ReturnCode.NODATA.getCode(),"获取openid成功|！",openId);
        return responseData;
    }


    @PassToken
    @PostMapping("user/query")
    @ApiOperation(value = "用户登入",notes = "code字段  404表示用户名或密码错误,200表示登入成功")
    public ResponseData userLogin(@RequestParam String uname , @RequestParam String upassword, HttpServletRequest request){
        User user = iUserService.userLoginByUsernameAndPassword(uname,upassword);
        //登入失败
        if(user == null){
            ResponseData responseData = new ResponseData(false, ReturnCode.NODATA.getCode(),"登入失败！");
            return responseData;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        //创建token
        String token = JwtHelperUtil.getToken(user);
        //设置返回信息
        map.put("user" , user);
        map.put("token", token);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"登入成功！",map);
        return responseData;
    }

    @PassToken
    @GetMapping("user/query/{unama}")
    @ApiOperation("查询此用户名是否已注册" )
    public ResponseData queryUname(@PathVariable String uname){
       boolean flag =  iUserService.queryUserByuname(uname);
       if(!flag){
           ResponseData responseData = new ResponseData(true,ReturnCode.NODATA.getCode(),"用户名不存在！");
           return responseData;
       }
        ResponseData responseData = new ResponseData(true,ReturnCode.NODATA.getCode(),"用户名已存在！");
        return responseData;
    }

    @UserLoginToken
    @PutMapping("user/{uid}")
    @ApiOperation(value = "修改用户当前状态",notes = "下线,隐身,如果退出登入请传一个下线过来！")
    public ResponseData updateStatus(@PathVariable int uid,@RequestParam String status,HttpServletRequest request){
        //int uid = (int) request.getSession().getAttribute("uid");
        iUserService.updateUserStatus(status,uid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"修改用户状态成功！");
        return responseData;
    }


    @PassToken
    @PostMapping("user")
    @ApiOperation(value = "注册用户",notes = "需要用户名,密码，真实姓名，用户性别，用户手机号，用户生日，用户地址，用户头像（可空）")
    public ResponseData regUser(@RequestBody @ModelAttribute User user, MultipartFile file) throws IOException {
        //上传图片路径
        String uploadPath = "D:\\tupian";
        File filePath = new File(uploadPath);

        //如果路径不存在则创建路径
        if (!filePath.exists()){
            filePath.mkdir();
        }

        if (file != null){
            //获取图片名称
            String uimgName = file.getOriginalFilename();
            //判断该图片是否为空  且 是否是图片
            if (uimgName != null && !"".equalsIgnoreCase(uimgName.trim()) && PictureJudgeUtil.isImageFile(uimgName)) {
                System.out.println(PictureJudgeUtil.isImageFile(uimgName));
                //设置图片全路径
                File file1 = new File(uploadPath + "\\" + UUID.randomUUID().toString() + PictureJudgeUtil.getFileType(uimgName));

                //设置图片全路径名称
                user.setUimg("http://localhost:8080/image/" + UUID.randomUUID().toString() + PictureJudgeUtil.getFileType(uimgName));
                //设置注册状态
                user.setSTATUS("下线");
                //设置状态码
                user.setCODE(404);

                //保存图片到本地
                file.transferTo(file1);

               iUserService.regUser(user);

                //设置响应数据
                ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"用户注册成功！");
                return responseData;
            }
        }

       /* //允许文件的类型
        String fileType = "pdf,png,jpg";
        System.out.println(user.toString());
        //获取图片名称
        String uimgName = file.getOriginalFilename();
        //获取文件后缀，判断是否是图片
        String extName = uimgName.substring(uimgName.indexOf(".") + 1).toLowerCase().trim();

        String[]  strFileType = fileType.split(",");
        for (String s:strFileType) {
            if(extName.equals(s)){
                //设置图片名称
                user.setUimg(System.currentTimeMillis() + uimgName);
                //设置注册状态
                user.setSTATUS("下线");
                //设置状态码
                user.setCODE(404);

                File file1 = new File("D:\\tupian\\" + uimgName);
                //保存图片到本地
                file.transferTo(file1);

                iUserService.regUser(user);

                //设置响应数据
                ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"用户注册成功！");
                return responseData;
            }
        }*/
        //设置响应数据
        ResponseData responseData = new ResponseData(true,ReturnCode.NODATA.getCode(),"用户注册失败！");
        return responseData;
    }
}
