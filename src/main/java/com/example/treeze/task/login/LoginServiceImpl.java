package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.User;
import com.example.treeze.exception.TokenNotFoundException;
import com.example.treeze.exception.UserDuplicateException;
import com.example.treeze.exception.UserNotFoundException;
import com.example.treeze.repository.UserRepository;
import com.example.treeze.response.Response;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.vo.login.LoginVo;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final AccessJwtToken accessJwtToken;

    @Override
    public Response login(LoginDto loginDto) throws Exception{
        UserVo userInfo = findUserInfoByUserIdAndUserPw(loginDto);
        TokenVo tokenInfo = generateTokenInfo(loginDto);
        LoginVo loginInfo = new LoginVo(userInfo, tokenInfo);
        return new Response("success", "로그인에 성공하였습니다.", loginInfo);
    }

    @Override
    public UserVo findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception {
        User userInfo = userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw());
        if(userInfo == null){
            throw new UserNotFoundException("아이디, 비밀번호를 확인해주세요.");
        }
        UserVo userInfoVo = new UserVo(userInfo.getUserSeq(), userInfo.getUserId());
        return userInfoVo;
    }

    @Override
    public TokenVo generateTokenInfo(LoginDto loginDto) throws Exception {
        String accessToken = accessJwtToken.generateAccessToken(loginDto);
        String refreshToken = UUID.randomUUID().toString();
        String expiration = CalendarUtil.getAddDayDatetime(1); // 토큰 만료 시간

        TokenVo tokenInfoVo = new TokenVo(accessToken, refreshToken, expiration);
        if(accessToken == null || refreshToken == null || expiration == null){
            throw new TokenNotFoundException("토큰 생성에 실패하였습니다.");
        }
        return tokenInfoVo;
    }

    @Override
    public Response signup(SignupDto signupDto) throws Exception {
        duplicateValidationUserId(signupDto);
        UserVo registMap = registUser(signupDto);
        return new Response("success", "회원가입에 성공하였습니다.");
    }

    @Override
    public void duplicateValidationUserId(SignupDto signupDto) throws Exception {
        User userInfo = userRepository.findByUserId(signupDto.userId());
        if(userInfo != null){
            throw new UserDuplicateException("이미 등록된 아이디입니다.");
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
            throw new UserNotFoundException("회원가입에 실패하였습니다.");
        }
        UserVo userVo = new UserVo(saveUser.getUserSeq(), saveUser.getUserId());

        return userVo;
    }


}
