package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailsImpl;
import com.kob.backend.service.user.account.LoginServcie;
import com.kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//service层方法统一注解
@Service
public class LoginServiceImpl implements LoginServcie {
    @Autowired
    private AuthenticationManager authenticationManager;//使用jwt登录的一个接口

    @Override
    public Map<String, String> getToken(String username, String password) {
        //封装用户 使得密码不以明文形式存储展现
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username,password);
        //判断是否登录成功 登录失败会自动报异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //有了用户信息后可以直接调用UserDetailsImpl开启用户权限得到用户信息
        //从authenticate中取出信息 强转为UserDetailsImpl类型得到用户权限信息
        UserDetailsImpl loginuser = (UserDetailsImpl)authenticate.getPrincipal();
        User user = loginuser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("token", jwt);

        return map;
    }
}
