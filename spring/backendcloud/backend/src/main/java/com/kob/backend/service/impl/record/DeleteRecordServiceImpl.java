package com.kob.backend.service.impl.record;

import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import com.kob.backend.service.record.DeleteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteRecordServiceImpl implements DeleteRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject deleteRecord(Integer id, User user) {
        JSONObject resp = new JSONObject();
        Record record = recordMapper.selectById(id);
        if(record == null){
            resp.put("result","record not found");
            return resp;
        }
        // 防止删除别人的录像
        if(!record.getUserId().equals(user.getId())){
            resp.put("result","permission denied");
            return resp;
        }
        recordMapper.deleteById(id);
        resp.put("result","success");
        return resp;
    }

}
