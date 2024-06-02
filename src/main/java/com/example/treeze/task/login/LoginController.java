package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.exception.LoginException;
import com.example.treeze.response.Response;
import com.example.treeze.util.MessageUtil;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    final Logger log = LogManager.getLogger(getClass());

    @PostMapping("/login")
    public Response postLogin(@Valid @RequestBody LoginDto loginDto) throws Exception {
        return loginService.login(loginDto);
    }

    @PostMapping("/signup")
    public Response postSignup(@Valid @RequestBody SignupDto signupDto) throws Exception {
        String userPassword = signupDto.userPw();
        String userPasswordConfirm = signupDto.userPwConfirm();

        validateEqualPassword(userPassword, userPasswordConfirm);

        return loginService.signup(signupDto);
    }

    public void validateEqualPassword(String userPassword, String userPasswordConfirm) throws Exception {
        if (!userPassword.equals(userPasswordConfirm)) {
            throw new LoginException(MessageUtil.OTHER_PASSWORD);
        }
    }
}