package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.exception.LoginException;
import com.example.treeze.response.Response;
import com.example.treeze.util.MessageUtil;
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
        signUpDtoValidate(signupDto);
        return loginService.signup(signupDto);
    }

    public void signUpDtoValidate(SignupDto signupDto) {
        String userId = signupDto.userId();
        String userPw = signupDto.userPw();
        String userPwConfirm = signupDto.userPwConfirm();
        String phoneNumber = signupDto.phoneNumber();
        log.debug("userId={}",userId,",userPw={}",userPw,
                ",userPwConfirm={}",userPwConfirm,",phoneNumber={}",phoneNumber);

        if(!userPw.equals(userPwConfirm)) {
            throw new LoginException(MessageUtil.OTHER_PASSWORD);
        }

        // 비밀번호는 대,소문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.
        String passwordRegularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        boolean isMatchPasswordExpression = Pattern.matches(passwordRegularExpression, userPw);
        if(!isMatchPasswordExpression) {
            throw new LoginException(MessageUtil.INVALID_PASSWORD);
        }

        /*01로 시작
		그 뒤에 0, 1, 6, 7, 8, 9 중 하나의 숫자
		첫 번째 하이픈(-)
		3자리 또는 4자리 숫자
		두 번째 하이픈(-)
		4자리 숫자*/
        String phoneNumberRegularExpressionKor = "^01(?:0|1|[6-9])-(?:\\d{4}|\\d{3})-\\d{4}$";
        boolean isMatchPhoneNumberExpression = Pattern.matches(phoneNumberRegularExpressionKor, phoneNumber);
        if(!isMatchPhoneNumberExpression) {
            throw new LoginException(MessageUtil.INVALID_PHONENUMBER);
        }
    }
}