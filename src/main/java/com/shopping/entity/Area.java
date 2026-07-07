package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("area")
public class Area {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private Integer level;

    private String name;

    private String pinyinPrefix;

    private String pinyin;

    private Long extId;

    private String extName;
}
