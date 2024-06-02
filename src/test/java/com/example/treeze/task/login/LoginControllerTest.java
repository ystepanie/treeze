package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.User;
import com.example.treeze.exception.LoginException;
import com.example.treeze.response.Response;
import com.example.treeze.util.MessageUtil;
import com.example.treeze.vo.login.LoginVo;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @Mock
    private LoginServiceImpl loginServiceImpl;

    @InjectMocks
    private LoginController loginController;

    private User user;
    private LoginDto loginDto;
    private UserVo userVo;
    private TokenVo tokenVo;
    private SignupDto signupDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserSeq(1);
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");

        loginDto = new LoginDto("id1", "Testtest123!");

        userVo = new UserVo(1, "id1");
        tokenVo = new TokenVo("accessToken", "refreshToken", "2024-01-01");
        signupDto = new SignupDto("id1", "Testtest123!", "Testtest123!", "010-7643-0423");
    }

    @Test
    void postLogin_success() throws Exception {
        //given
        LoginVo loginVo = new LoginVo(userVo, tokenVo);
        Response expectedResponse = new Response("success", MessageUtil.LOGIN_SUCCESS, loginVo);
        given(loginServiceImpl.login(any(LoginDto.class))).willReturn(expectedResponse);
        //when
        Response actualResponse = loginController.postLogin(loginDto);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void postLogin_userNotExist() throws Exception {
        //given
        LoginVo loginVo = new LoginVo(userVo, tokenVo);
        given(loginServiceImpl.login(any(LoginDto.class))).willThrow(new LoginException(MessageUtil.USER_NOT_EXIST));
        //when
        //then
        LoginException loginException = assertThrows(LoginException.class, () -> {
            // When
            loginController.postLogin(loginDto);
        });
        assertEquals(MessageUtil.USER_NOT_EXIST, loginException.getMessage());
    }

    @Test
    void postLogin_tokenFailed() throws Exception {
        //given
        LoginVo loginVo = new LoginVo(userVo, tokenVo);
        given(loginServiceImpl.login(any(LoginDto.class))).willThrow(new LoginException(MessageUtil.TOKEN_FAILED));
        //when
        //then
        LoginException loginException = assertThrows(LoginException.class, () -> {
            // When
            loginController.postLogin(loginDto);
        });
        assertEquals(MessageUtil.TOKEN_FAILED, loginException.getMessage());
    }

    @Test
    void postSignup_success() throws Exception {
        //given
        Response expectedResponse = new Response("success", MessageUtil.SIGNUP_SUCCESS, userVo);
        given(loginServiceImpl.signup(any(SignupDto.class))).willReturn(expectedResponse);
        //when
        Response actualResponse = loginController.postSignup(signupDto);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void postSignup_otherPassword() throws Exception {
        //given
        SignupDto diffPasswordSignupDto = new SignupDto("id1", "pw1", "pw2", "010-7643-0423");
        LoginException loginException = new LoginException(MessageUtil.OTHER_PASSWORD);
        //when
        LoginException resultException = assertThrows(LoginException.class, () -> {
            loginController.postSignup(diffPasswordSignupDto);
        });
        //then
        assertEquals(loginException.getMessage(), resultException.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"testtest123!", "TESTTEST123!", "TESTTEST!", "Testtest123", "Test1!"})
    void postSignup_validationPassword(String password) throws Exception {
        //given
        SignupDto invalidPasswordSignupDto = new SignupDto("id1", password, password, "010-7643-0423");
        LoginException loginException = new LoginException(MessageUtil.INVALID_PASSWORD);
        //when
        LoginException resultException = assertThrows(LoginException.class, () -> {
            loginController.postSignup(invalidPasswordSignupDto);
        });
        //then
        assertEquals(loginException.getMessage(), resultException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"012-1234-5678", "01012345678", "010-12345-678", "010-123-45678"})
    void postSignup_validationPhonenumber(String phonenumber) throws Exception {
        //given
        SignupDto invalidPhonenumberSignupDto = new SignupDto("id1", "Testtest123!", "Testtest123!", phonenumber);
        LoginException loginException = new LoginException(MessageUtil.INVALID_PHONENUMBER);
        //when
        LoginException resultException = assertThrows(LoginException.class, () -> {
            loginController.postSignup(invalidPhonenumberSignupDto);
        });
        //then
        assertEquals(loginException.getMessage(), resultException.getMessage());
    }
}