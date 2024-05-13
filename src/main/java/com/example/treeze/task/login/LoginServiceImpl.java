package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.entity.User;
import com.example.treeze.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    UserRepository userRepository;

    @Override
    public Map<String, Object> login(LoginDto loginDto) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> validateLoginResult = validateLogin(loginDto);
        if("false".equals(validateLoginResult.get("success"))) {
            resultMap.put("success", false);
            resultMap.put("message", String.valueOf(validateLoginResult.get("message")));
            return resultMap;
        }

        User loginUserInfo = findUserInfoByUserIdAndUserPw(loginDto);
        if(loginUserInfo == null) {
            resultMap.put("success", false);
            resultMap.put("message", String.valueOf(validateLoginResult.get("message")));
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("message", "로그인에 성공하였습니다.");
        resultMap.put("loginUserInfo", loginUserInfo);

        return resultMap;
    }

    @Override
    public User findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception {
        User userInfo = userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw());
        return userInfo;
    }

    @Override
    public Map<String, Object> validateLogin(LoginDto loginDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String userId = loginDto.userId();
        String userPw = loginDto.userPw();

        if(userId.isEmpty()) {
            resultMap.put("success", false);
            resultMap.put("message", "아이디를 입력해주세요.");
            return resultMap;
        }

        if(userPw.isEmpty()) {
            resultMap.put("success", false);
            resultMap.put("message", "비밀번호를 입력해주세요.");
            return resultMap;
        }

        resultMap.put("success", true);
        return resultMap;
    }
}
