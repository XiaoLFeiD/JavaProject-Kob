package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


//前后端分离写法
//后端注解的api返回的是一些数据

@RestController
//前后端分离写法 用RestController
//RequestMapping会将所有请求映射过来 包括get和post请求
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public Map<String,String> getBotInfo(){
        Map<String,String> bot = new HashMap<>();
        bot.put("name","apple");
        bot.put("rating","1500");
        return bot;
    }
}
