package com.zy.interceptors;


import com.zy.utils.JwtUtil;
import com.zy.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//登陆拦截器，检查是否登录
@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        try{
            //获取redis里的token
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String redisToken=stringStringValueOperations.get(token);
            if(redisToken==null){
                //把错误扔到下面catch
                throw new RuntimeException();
            }
            Map<String ,Object> claims= JwtUtil.parseToken(token);
            //把token携带的用户数据存在ThreadLocal
            ThreadLocalUtil.set(claims);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        controller返回给前端，前端渲染完以后清理掉存在ThreadLocal的数据
        ThreadLocalUtil.remove();
    }
}
