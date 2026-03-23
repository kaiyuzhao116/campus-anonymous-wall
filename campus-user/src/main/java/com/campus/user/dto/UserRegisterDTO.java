package com.campus.user.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String studentId;
    private String studentName;
    private String password;
}