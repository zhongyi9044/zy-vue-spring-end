package com.zy.service.impl;

import com.zy.mapper.UserMapper;
import com.zy.pojo.User;
import com.zy.service.UserService;
import com.zy.utils.Md5Util;
import com.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User tempUser=userMapper.findByUserName(username);
        return tempUser;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5Password=Md5Util.getMD5String(password);
        userMapper.register(username,md5Password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> claims= ThreadLocalUtil.get();
        Integer id=(Integer) claims.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> claims= ThreadLocalUtil.get();
        Integer id=(Integer) claims.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
