package com.zy.controller;

import com.zy.pojo.Result;
import com.zy.pojo.User;
import com.zy.service.UserService;
import com.zy.utils.JwtUtil;
import com.zy.utils.Md5Util;
import com.zy.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    //redis,用于校验token是否有效，此处用于修改密码场景
    //redis具体作用：可以存储键值对比如token，当我们取值的时候可以和当前token进行对比，查看token是否有效
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$")String username,@Pattern(regexp = "^\\S{5,16}$")String password){
        //查找用户
        User tempUser=userService.findByUserName(username);
        if(tempUser==null){
            //注册
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已经存在");
        }
    }

//    登录
    @PostMapping("login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$")String username,@Pattern(regexp = "^\\S{5,16}$")String password){
//        查找用户
        User tempUser=userService.findByUserName(username);
        if(tempUser==null){
            //没找到
            return Result.error("用户名错误");
        }else{
            //找到了
            // 判断密码是否正确
            if(Md5Util.getMD5String(password).equals(tempUser.getPassword())){
                Map<String,Object> claims=new HashMap<>();
                claims.put("id",tempUser.getId());
                claims.put("username",tempUser.getUsername());
                String token=JwtUtil.genToken(claims);
                //键值对存储的地方
                ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
                //存键值对
                stringStringValueOperations.set(token,token,1, TimeUnit.HOURS);
                return Result.success(token);
            }else {
                return Result.error("密码错误");
            }
        }

    }

//    获取用户信息
    @GetMapping("/userInfo")
    public Result<User> getUserInfo(){
        //根据存在TreadLocal里的用户名查询用户
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String)map.get("username");
        User tempUser=userService.findByUserName(username);

        return Result.success(tempUser);
    }

//    修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

//    修改头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

//        修改密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params , @RequestHeader("Authorization") String token){
        String oldPwd=params.get("old_pwd");
        String newPwd=params.get("new_pwd");
        String rePwd=params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) ||!StringUtils.hasLength(newPwd) ||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少参数");
        }
//        比对原密码
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String)map.get("username");
        User tempUser=userService.findByUserName(username);
        if(!tempUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        String regexp = "^\\S{5,16}$";
        if(!newPwd.matches(regexp)){
            return Result.error("新密码格式错误");
        }
//        比对新密码和确认密码是否相同
        if(!newPwd.equals(rePwd)){
            return Result.error("确认密码失败");
        }
        userService.updatePwd(newPwd);
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.getOperations().delete(token);
        return Result.success();
    }
}
