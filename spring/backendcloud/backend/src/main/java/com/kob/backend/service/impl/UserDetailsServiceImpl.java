package com.kob.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//实现service.impl.UserDetailsServiceImpl类，继承自UserDetailsService接口，用来接入数据库信息
//通过用户输入的username查询数据库 并且返回数据库中用户的信息
//然后调用UserDetailsImpl 开启相关用户信息
//有了用户信息后可以直接调用UserDetailsImpl开启用户权限得到用户信息
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired//使用mapper时候注解Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return new UserDetailsImpl(user);

    }
}
