package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

//一张表对应一个pojo 一个mapper
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
