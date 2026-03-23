package com.campus.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String studentId; // 学号
    private String studentName; // 姓名
    private String password; // 加密密码
    private Integer status; // 状态 1正常 0封禁
    private LocalDateTime createTime; // 创建时间
}