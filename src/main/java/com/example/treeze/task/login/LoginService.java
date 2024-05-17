package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;

import java.util.Map;

public interface LoginService {
    public Map<String, Object> login(LoginDto loginDto) throws Exception;

    public Map<String, Object> findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception;

    public Map<String, Object> generateTokenInfo(LoginDto loginDto) throws Exception;

    public Map<String, Object> signup(SignupDto signupDto) throws Exception;

    public Map<String, Object> registUser(SignupDto signupDto) throws Exception;

}
