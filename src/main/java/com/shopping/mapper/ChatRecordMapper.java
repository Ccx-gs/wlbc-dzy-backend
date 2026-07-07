package com.shopping.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopping.entity.ChatRecord;

@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
}