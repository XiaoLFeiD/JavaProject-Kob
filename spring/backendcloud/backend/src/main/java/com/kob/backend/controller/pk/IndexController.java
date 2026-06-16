package com.kob.backend.controller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

////@Controller和@RestController都是用于处理HTTP请求的注解
////@Controller主要用于返回视图，而@RestController主要用于返回JSON或XML格式的数据
//在controller的注解下寻找对应api 返回前端文件html
//属于前后端不分离的写法
@Controller
//该Controller注解的后端函数全部在父目录-pk目录下
@RequestMapping("/pk/")
public class IndexController {
    //在pk目录下找index目录
    @RequestMapping("index")
    public String index(){
        return "pk/index.html";
    }
}
