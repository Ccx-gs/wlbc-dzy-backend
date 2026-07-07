package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chat_record")
public class ChatRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String userMsg;
    private String aiReply;
    private LocalDateTime sendTime;
    @TableLogic
    private Integer isDeleted;
}