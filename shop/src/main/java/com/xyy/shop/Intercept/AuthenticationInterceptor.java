package com.xyy.shop.Intercept;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.xyy.shop.annotates.PassToken;
import com.xyy.shop.annotates.UserLoginToken;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.service.users.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 身份认证拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //从http请求头中取出token
        String token = request.getHeader("token");

        //如果不是映射方法直接通过
        if (!(object instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //判断是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }

        //检查有没有需要需要用户权限注册
        if (method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);

            //是否需要认证
            if (userLoginToken.required()){
                //执行认证
                if (token == null){
                    throw new RuntimeException("无token，请重新登入！");
                }

                String uid ;
                try{
                    //获取在Payload中的uid信息
                    uid = JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException j){
                    throw new RuntimeException("401");
                }

                User user = iUserService.queryUserByUid(Integer.parseInt(uid));
                if (user == null){
                    throw new RuntimeException("用户不存在,请重新登入！");
                }

                //验证token
                JWTVerifier jwtVerifier =  JWT.require(Algorithm.HMAC256(user.getUpassword())).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTDecodeException e){
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
