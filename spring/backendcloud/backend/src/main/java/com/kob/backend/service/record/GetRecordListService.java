package com.kob.backend.service.record;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.pojo.User;

public interface GetRecordListService {
    JSONObject getList(Integer page, User user);
}
