package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.entity.User;
import com.example.treeze.repository.UserRepository;
import com.example.treeze.response.Response;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.vo.login.LoginVo;
import com.example.treeze.vo.login.TokenVo;
import com.example.treeze.vo.login.UserVo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessJwtToken accessJwtToken;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> login(LoginDto loginDto) throws Exception{
        Map<String, Object> loginUserInfoMap = findUserInfoByUserIdAndUserPw(loginDto);
        if("failed".equals(String.valueOf(loginUserInfoMap.get("status")))) {
            return loginUserInfoMap;
        }

        Map<String, Object> tokenMap = generateTokenInfo(loginDto);

        UserVo userInfo = (UserVo) loginUserInfoMap.get("userInfo");
        TokenVo tokenInfo = (TokenVo) tokenMap.get("tokenInfo");
        LoginVo loginInfo = new LoginVo(userInfo, tokenInfo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("message", "로그인에 성공하였습니다.");
        resultMap.put("data", loginInfo);
        return resultMap;
    }

    @Override
    public Map<String, Object> findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception {
        User userInfo = userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw());

        if(userInfo == null) {
            Map<String, Object> resultFailMap = new HashMap<>();
            resultFailMap.put("status", "failed");
            resultFailMap.put("message", "아이디 및 패스워드를 확인해주세요.");
            return resultFailMap;
        }

        UserVo userInfoVo = new UserVo(userInfo.getUserSeq(), userInfo.getUserId());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("userInfo", userInfoVo);
        return resultMap;
    }

    @Override
    public Map<String, Object> generateTokenInfo(LoginDto loginDto) throws Exception {
        String accessToken = accessJwtToken.generateAccessToken(loginDto);
        String refreshToken = UUID.randomUUID().toString();
        String expiration = CalendarUtil.getAddDayDatetime(1); // 토큰 만료 시간

        TokenVo tokenInfoVo = new TokenVo(accessToken, refreshToken, expiration);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("tokenInfo", tokenInfoVo);
        return resultMap;
    }

    @Override
    public Map<String, Object> signup(SignupDto signupDto) throws Exception {
        User userInfo = userRepository.findByUserId(signupDto.userId());
        if(userInfo != null) {
            Map<String, Object> resultFailMap = new HashMap<>();
            resultFailMap.put("status", "failed");
            resultFailMap.put("message", "이미 존재하는 아이디입니다.");
            return resultFailMap;
        }

        Map<String, Object> registMap = registUser(signupDto);


    }

    @Override
    public Map<String, Object> registUser(SignupDto signupDto) throws Exception {
        User user = new User();
        user.setUserId(signupDto.userId());
        user.setUserPw(signupDto.userPw());
        user.setPhoneNumber(signupDto.phoneNumber());
        User saveUser = userRepository.save(user);
        if(saveUser == null || saveUser.getUserSeq() == null) {

        }
    }


}
