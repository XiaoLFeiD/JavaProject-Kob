package com.kob.backend.controller.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailsImpl;
import com.kob.backend.service.record.DeleteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteRecordController {
    @Autowired
    private DeleteRecordService deleteRecordService;

    @PostMapping("/record/delete/")
    JSONObject deleteRecordMap(@RequestParam Map<String, String> data){
        Integer recordid = Integer.parseInt(data.get("record_id"));
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginuser.getUser();
        return deleteRecordService.deleteRecord(recordid, user);
    }
}
