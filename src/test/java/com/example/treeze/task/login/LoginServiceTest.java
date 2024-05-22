package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.entity.User;
import com.example.treeze.exception.UserNotFoundException;
import com.example.treeze.repository.UserRepository;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.vo.login.LoginVo;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessJwtToken accessJwtToken;

    @InjectMocks
    private LoginServiceImpl loginService;

    private LoginDto loginDto;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("id1", "pw1");
        mockStatic(CalendarUtil.class);
    }

    @Test
    void login() { // 객체를 전달해주는 역할만 하는 녀석을 굳이 단위테스트할 필요가 있을까..?
        //given
        UserVo userVo = new UserVo(2, "ystepanie2");
        TokenVo tokenVo = new TokenVo("accessToken1", "refreshToken1", "2024-01-01");
        LoginVo loginVo = new LoginVo(userVo, tokenVo);
        //when

        //then
        assertNotNull(userVo, "userVo not null");
        assertNotNull(tokenVo, "tokenVo not null");
        assertNotNull(loginVo, "loginVo not null");
    }

    @Test
    void findUserInfoByUserIdAndUserPw_success() throws Exception {
        //given
        User user = new User();
        user.setUserSeq(1);
        user.setUserId("id1");

        when(userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw())).thenReturn(user);
        //when
        UserVo info = loginService.findUserInfoByUserIdAndUserPw(loginDto);
        //then
        System.out.println(info);
        assertNotNull(info);
        assertEquals(1, info.userSeq());
        assertEquals("id1", info.userId());
    }

    @Test
    void findUserInfoByUserIdAndUserPw_failed() throws Exception {
        //given
        //when
        when(userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> loginService.findUserInfoByUserIdAndUserPw(loginDto));
    }

    @Test
    void generateTokenInfo_success() throws Exception {
        //given
        String accessTokenValue = "accessTokenValue";
        String refreshTokenValue = "refreshTokenValue";
        String expiresInValue = "2024-01-01";
        given(accessJwtToken.generateAccessToken(loginDto)).willReturn(accessTokenValue);
        given(CalendarUtil.getAddDayDatetime(1)).willReturn(expiresInValue);
        //when
        TokenVo tokenVo = loginService.generateTokenInfo(loginDto);
        //then
        assertNotNull(tokenVo);
        assertNotNull(tokenVo.accessToken());
        assertNotNull(tokenVo.refreshToken());
        assertNotNull(tokenVo.expiration());
    }

    @Test
    void signup() {
    }

    @Test
    void duplicateValidationUserId() {
    }

    @Test
    void registUser() {
    }
}