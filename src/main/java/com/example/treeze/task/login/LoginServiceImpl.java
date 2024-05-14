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

        Map<String, Object> validateLoginMap = validateLogin(loginDto);
        if("failed".equals(String.valueOf(validateLoginMap.get("status")))) {
            return validateLoginMap;
        }

        Map<String, Object> loginUserInfoMap = findUserInfoByUserIdAndUserPw(loginDto);
        if("failed".equals(String.valueOf(loginUserInfoMap.get("status")))) {
            return loginUserInfoMap;
        }

        resultMap.put("status", "success");
        resultMap.put("message", "로그인에 성공하였습니다.");
        resultMap.put("data", loginUserInfoMap.get("userInfo"));
        return resultMap;
    }

    @Override
    public Map<String, Object> findUserInfoByUserIdAndUserPw(LoginDto loginDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        User userInfo = userRepository.findByUserIdAndUserPw(loginDto.userId(), loginDto.userPw());
        if(userInfo == null) {
            resultMap.put("status", "failed");
            resultMap.put("message", "아이디 및 패스워드를 확인해주세요.");
            return resultMap;
        }

        resultMap.put("status", "success");
        resultMap.put("userInfo", userInfo);
        return resultMap;
    }

    @Override
    public Map<String, Object> validateLogin(LoginDto loginDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String userId = loginDto.userId();
        String userPw = loginDto.userPw();

        if(userId.isEmpty()) {
            resultMap.put("status", "failed");
            resultMap.put("message", "아이디를 입력해주세요.");
            return resultMap;
        }

        if(userPw.isEmpty()) {
            resultMap.put("status", "failed");
            resultMap.put("message", "비밀번호를 입력해주세요.");
            return resultMap;
        }

        resultMap.put("status", "success");
        return resultMap;
    }
}
