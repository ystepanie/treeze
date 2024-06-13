package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.User;
import com.example.treeze.exception.BadRequestException;
import com.example.treeze.repository.UserRepository;
import com.example.treeze.response.Response;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.util.MessageUtil;
import com.example.treeze.vo.login.LoginVo;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final AccessJwtToken accessJwtToken;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Response login(LoginDto loginDto) throws Exception{
        String userId = loginDto.userId();
        String userPw = loginDto.userPw();

        UserVo userInfo = findUserInfoByUserId(userId);
        String encUserPw = userInfo.userPw();

        passwordMatch(userPw, encUserPw);
        TokenVo tokenInfo = generateTokenInfo(loginDto);
        LoginVo loginInfo = new LoginVo(userInfo, tokenInfo);
        return new Response("success", MessageUtil.LOGIN_SUCCESS, loginInfo);
    }

    @Override
    public UserVo findUserInfoByUserId(String usreId) throws Exception {
        User userInfo = userRepository.findByUserId(usreId);
        if(userInfo == null){
            throw new BadRequestException(MessageUtil.USER_NOT_EXIST);
        }
        UserVo userInfoVo = new UserVo(userInfo.getUserSeq(), userInfo.getUserId(), userInfo.getUserPw());
        return userInfoVo;
    }

    @Override
    public void passwordMatch(String userPassword, String encUserPassword) throws Exception {
        if (!passwordEncoder.matches(userPassword, encUserPassword)) {
            throw new BadRequestException(MessageUtil.DIFF_PASSWORD);
        }
    }

    @Override
    public TokenVo generateTokenInfo(LoginDto loginDto) throws Exception {
        String accessToken = accessJwtToken.generateAccessToken(loginDto);
        String refreshToken = UUID.randomUUID().toString();
        String expiration = CalendarUtil.getAddDayDatetime(1); // 토큰 만료 시간
        if(accessToken == null || refreshToken == null || expiration == null || loginDto == null){
            throw new BadRequestException(MessageUtil.TOKEN_FAILED);
        }

        TokenVo tokenInfoVo = new TokenVo(accessToken, refreshToken, expiration);
        return tokenInfoVo;
    }

    @Override
    public Response signup(SignupDto signupDto) throws Exception {
        SignupDto encodingSignupDto = signupEncoding(signupDto);
        duplicateValidationUserId(encodingSignupDto);
        UserVo userVo = registUser(encodingSignupDto);
        return new Response("success", MessageUtil.SIGNUP_SUCCESS, userVo);
    }

    @Override
    public SignupDto signupEncoding(SignupDto signupDto) throws Exception {
        SignupDto encodingSignupDto = SignupDto.builder()
                .userId(signupDto.userId())
                .userPw(passwordEncoder.encode(signupDto.userPw()))
                .userPwConfirm(signupDto.userPwConfirm())
                .phoneNumber(signupDto.phoneNumber())
                .build();

        return encodingSignupDto;
    }

    @Override
    public void duplicateValidationUserId(SignupDto signupDto) throws Exception {
        User userInfo = userRepository.findByUserId(signupDto.userId());
        if(userInfo != null){
            throw new BadRequestException(MessageUtil.USER_ALREADY_EXIST);
        }
    }

    @Override
    public UserVo registUser(SignupDto signupDto) throws Exception {
        User user = new User();
        user.setUserId(signupDto.userId());
        user.setUserPw(signupDto.userPw());
        user.setPhoneNumber(signupDto.phoneNumber());
        User saveUser = userRepository.save(user);
        if(saveUser == null || saveUser.getUserSeq() == 0){
            throw new BadRequestException(MessageUtil.SIGNUP_FAILED);
        }

        UserVo userVo = UserVo.builder()
                .userSeq(saveUser.getUserSeq())
                .userId(saveUser.getUserId())
                .build();

        return userVo;
    }


}
