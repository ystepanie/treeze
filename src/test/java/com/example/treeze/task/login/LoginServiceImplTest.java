package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.User;
import com.example.treeze.exception.LoginException;
import com.example.treeze.repository.UserRepository;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.util.MessageUtil;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessJwtToken accessJwtToken;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginServiceImpl loginServiceImpl;

    private LoginDto loginDto;
    private SignupDto signupDto;

    private MockedStatic<CalendarUtil> mockedStatic;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("id1", "pw1");
        mockedStatic = mockStatic(CalendarUtil.class);
        signupDto = new SignupDto("id1", "pw1", "pw1", "010-7643-0423");
    }

    @AfterEach
    public void tearDown() {
        // Static mocking cleanup
        mockedStatic.close();
    }

    @Test
    void login() { // 객체를 전달해주는 역할만 하는 녀석을 굳이 단위테스트할 필요가 있을까..?
    }
    
    @Test
    void passwordMatch_failed() throws Exception {
        //given
        String userPassword = "pw1";
        String encUserPassword = "encPw1";

        //when
        when(passwordEncoder.matches(userPassword, encUserPassword)).thenReturn(false);

        //then
        LoginException loginException = assertThrows(LoginException.class, () -> loginServiceImpl.passwordMatch(userPassword, encUserPassword));
        assertEquals(loginException.getMessage(), MessageUtil.DIFF_PASSWORD);
    }

    @Test
    void findUserInfoByUserId_success() throws Exception {
        //given
        User user = new User();
        user.setUserSeq(1);
        user.setUserId("id1");

        when(userRepository.findByUserId(loginDto.userId())).thenReturn(user);
        //when
        UserVo info = loginServiceImpl.findUserInfoByUserId(loginDto.userId());
        //then
        assertNotNull(info);
        assertEquals(1, info.userSeq());
        assertEquals("id1", info.userId());
    }

    @Test
    void findUserInfoByUserId_nullException() throws Exception {
        //given
        //when
        when(userRepository.findByUserId(loginDto.userId())).thenReturn(null);
        //then
        assertThrows(LoginException.class, () -> loginServiceImpl.findUserInfoByUserId(loginDto.userId()));
    }

    @Test
    void generateTokenInfo_success() throws Exception {
        //given
        String accessTokenValue = "accessTokenValue";
        String expiresInValue = "2024-01-01";
        given(accessJwtToken.generateAccessToken(loginDto)).willReturn(accessTokenValue);

        given(CalendarUtil.getAddDayDatetime(1)).willReturn(expiresInValue);
        //when
        TokenVo tokenVo = loginServiceImpl.generateTokenInfo(loginDto);
        //then
        assertNotNull(tokenVo);
        assertNotNull(tokenVo.accessToken());
        assertNotNull(tokenVo.refreshToken());
        assertNotNull(tokenVo.expiration());
    }

    private static Stream<Arguments> tokenNullArguments() {
        return Stream.of(
                Arguments.of(null, "mockAccessToken", "mockExpiration"),
                Arguments.of(new LoginDto("testUser", "testPassword"), null, "mockExpiration"),
                Arguments.of(new LoginDto("testUser", "testPassword"), "mockAccessToken", null)
        );
    }

    @ParameterizedTest
    @MethodSource("tokenNullArguments")
    void generateTokenInfo_nullException(LoginDto loginDto, String accessToken, String expiration) throws Exception {
        // given
        if (loginDto != null) {
            given(accessJwtToken.generateAccessToken(any(LoginDto.class))).willReturn(accessToken);
        } else {
            given(accessJwtToken.generateAccessToken(any())).willThrow(new LoginException(MessageUtil.TOKEN_FAILED));
        }
        if (expiration != null) {
            given(CalendarUtil.getAddDayDatetime(1)).willReturn(expiration);
        } else {
            given(CalendarUtil.getAddDayDatetime(1)).willReturn(null);
        }
        //when
        //then
        assertThrows(LoginException.class, () -> {
            // When
            loginServiceImpl.generateTokenInfo(loginDto);
        });
    }

    @Test
    void signup() throws Exception {
    }

    @Test
    void duplicateValidationUserId_success() throws Exception {
        //given
        given(userRepository.findByUserId(anyString())).willReturn(null);
        //when
        loginServiceImpl.duplicateValidationUserId(signupDto);
        //then
        verify(userRepository).findByUserId(anyString());
    }

    @Test
    void duplicateValidationUserId_userExist() throws Exception {
        //given
        User user = new User();
        user.setUserSeq(1);
        user.setUserId("id1");
        given(userRepository.findByUserId(signupDto.userId())).willReturn(user);
        //when
        Exception exception = assertThrows(LoginException.class, () -> {
            loginServiceImpl.duplicateValidationUserId(signupDto);
        });
        //then
        assertEquals(user.getUserId(), signupDto.userId());
        assertEquals(MessageUtil.USER_ALREADY_EXIST, exception.getMessage());
        verify(userRepository).findByUserId(anyString());
    }

    @Test
    void registUser_success() throws Exception {
        //given
        User user = new User();
        user.setUserSeq(1);
        user.setUserId(signupDto.userId());
        user.setUserPw(signupDto.userPw());
        user.setPhoneNumber(signupDto.phoneNumber());
        given(userRepository.save(any(User.class))).willReturn(user);
        //when
        UserVo userVo = loginServiceImpl.registUser(signupDto);
        //then
        assertNotNull(user);
        assertEquals(user.getUserSeq(), userVo.userSeq());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registUser_userIsNull() throws Exception {
        //given
        given(userRepository.save(any(User.class))).willReturn(null);
        //when
        Exception exception = assertThrows(LoginException.class, () -> {
            loginServiceImpl.registUser(signupDto);
        });
        //then
        assertEquals(MessageUtil.SIGNUP_FAILED, exception.getMessage());
        verify(userRepository).save(any(User.class));
    }
}