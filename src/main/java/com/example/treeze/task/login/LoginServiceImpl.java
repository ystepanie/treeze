package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.login.Token;
import com.example.treeze.entity.login.User;
import com.example.treeze.exception.BadRequestException;
import com.example.treeze.repository.login.TokenRepository;
import com.example.treeze.repository.login.UserRepository;
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
    private final TokenRepository tokenRepository;
    private final AccessJwtToken accessJwtToken;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Response login(LoginDto loginDto) throws Exception{
        String userId = loginDto.userId();
        String userPw = loginDto.userPw();
        // 아이디, 비밀번호 조회
        UserVo userInfo = findUserInfoByUserId(userId);
        String encUserPw = userInfo.userPw();
        // 패스워드 매치 확인
        passwordMatchException(userPw, encUserPw);

        // 토큰 생성
        TokenVo tokenInfo = generateTokenInfo(loginDto);
        Token token = new Token();
        token.setUserSeq(userInfo.userSeq());
        token.setRefreshToken(tokenInfo.refreshToken());
        token.setTokenExpiration(tokenInfo.expiration());

        // 리프레시 토큰 저장
        insertRefreshToken(token);

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
    public void passwordMatchException(String userPassword, String encUserPassword) throws Exception {
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
    public void insertRefreshToken(Token token) throws BadRequestException {
        Long userSeq = token.getUserSeq();
        String refreshToken = token.getRefreshToken();
        String tokenExpiration = token.getTokenExpiration();
        if(userSeq == null || userSeq == 0L) {
            throw new BadRequestException(MessageUtil.USER_NOT_EXIST);
        }
        tokenRepository.upsert(userSeq, refreshToken, tokenExpiration);
        token.setRefreshToken(""); //db에서 관리하므로 빈값으로 설정
        //todo 예외처리에 관해
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
