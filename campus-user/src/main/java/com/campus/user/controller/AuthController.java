package com.campus.user.controller;

import com.alibaba.nacos.api.model.v2.Result;
import com.campus.user.dto.UserLoginDTO;
import com.campus.user.dto.UserRegisterDTO;
import com.campus.user.entity.SysUser;
import com.campus.user.service.SysUserService;
import common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 无邮箱版用户认证控制器
 * 核心：学号+密码 注册/登录
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService userService;

    // BCrypt加密器（Spring Security内置，安全可靠）
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 1. 注册接口（学号+姓名+密码）
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO dto) {
        // 校验参数
        if (dto.getStudentId() == null || dto.getStudentId().trim().isEmpty()) {
            throw new BusinessException("学号不能为空");
        }
        if (dto.getStudentName() == null || dto.getStudentName().trim().isEmpty()) {
            throw new BusinessException("姓名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            throw new BusinessException("密码长度不能少于6位");
        }

        // 校验学号是否已注册
        boolean exists = userService.lambdaQuery()
                .eq(SysUser::getStudentId, dto.getStudentId())
                .exists();
        if (exists) {
            throw new BusinessException("该学号已注册");
        }

        // 密码加密
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 保存用户
        SysUser user = new SysUser();
        user.setStudentId(dto.getStudentId());
        user.setStudentName(dto.getStudentName());
        user.setPassword(encodedPassword);
        user.setStatus(1);
        userService.save(user);

        return Result.success("注册成功，请登录");
    }

    /**
     * 2. 登录接口（学号+密码）
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDTO dto) {
        // 1. 查询用户
        SysUser user = userService.lambdaQuery()
                .eq(SysUser::getStudentId, dto.getStudentId())
                .one();
        if (user == null) {
            throw new BusinessException("学号未注册");
        }

        // 2. 校验账号状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被封禁");
        }

        // 3. 校验密码
        boolean passwordMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new BusinessException("密码错误");
        }

        // 4. 生成 Token（测试版，后续替换为 JJWT/Sa-Token）
        String token = "campus-wall-token-" + user.getId();

        return Result.success(token);
    }

    /**
     * 3. 测试接口：根据Token获取用户信息（简化版）
     */
    @PostMapping("/user-info")
    public Result<SysUser> getUserInfo(@RequestParam String token) {
        // 简化逻辑：从Token中提取userId（正式版用JWT解析）
        Long userId = Long.parseLong(token.replace("campus-wall-token-", ""));
        SysUser user = userService.getById(userId);
        // 脱敏：返回时去掉密码
        user.setPassword(null);
        return Result.success(user);
    }
}
