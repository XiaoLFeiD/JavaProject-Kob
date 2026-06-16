package com.kob.backend.service.record;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.pojo.User;

public interface DeleteRecordService {
    JSONObject deleteRecord(Integer id, User user);
}
