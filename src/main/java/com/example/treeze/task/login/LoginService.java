package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.entity.User;

import java.util.Map;

public interface LoginService {
    public Map<String, Object> login(LoginDto loginDto) throws Exception;

    public User findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception;

    public Map<String, Object> validateLogin(LoginDto loginDto) throws Exception;
}
