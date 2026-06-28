package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private Integer sortOrder;
    private Integer status;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
