package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    final Logger log = LogManager.getLogger(getClass());

    @PostMapping("/login")
    public Response postLogin(@RequestBody LoginDto loginDto) throws Exception {
        return loginService.login(loginDto);
    }

    @PostMapping("/signup")
    public Response postSignup(@RequestBody SignupDto signupDto) throws Exception {
        String userId = signupDto.userId();
        String userPw = signupDto.userPw();
        String userPwConfirm = signupDto.userPwConfirm();
        String phoneNumber = signupDto.phoneNumber();
        log.debug("userId={}",userId,",userPw={}",userPw,
                ",userPwConfirm={}",userPwConfirm,",phoneNumber={}",phoneNumber);

        if(!userPw.equals(userPwConfirm)) {
            return new Response("failed", "비밀번호 확인값이 서로 다릅니다.");
        }

        String passwordRegularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        boolean isMatchPasswordExpression = Pattern.matches(passwordRegularExpression, userPw);
        if(!isMatchPasswordExpression) {
            return new Response("failed", "비밀번호는 대,소문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.");
        }

        String phoneNumberRegularExpressionKor = "^01(?:0|1|[6-9])-(?:\\d{4}|\\d{3})-\\d{4}$";
        boolean isMatchPhoneNumberExpression = Pattern.matches(phoneNumberRegularExpressionKor, phoneNumber);
        if(!isMatchPhoneNumberExpression) {
            return new Response("failed", "휴대폰 번호를 확인해 주세요.");
        }

        return loginService.signup(signupDto);
    }
}