package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.response.Response;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;

public interface LoginService {
    public Response login(LoginDto loginDto) throws Exception;

    public UserVo findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception;

    public TokenVo generateTokenInfo(LoginDto loginDto) throws Exception;

    public Response signup(SignupDto signupDto) throws Exception;

    public void duplicateValidationUserId(SignupDto signupDto) throws Exception;

    public UserVo registUser(SignupDto signupDto) throws Exception;

}
