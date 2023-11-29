package com.zy.service;

import com.zy.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {

    //根据用户名查找用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);

    //更新
    void update(User user);

//    修改头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
