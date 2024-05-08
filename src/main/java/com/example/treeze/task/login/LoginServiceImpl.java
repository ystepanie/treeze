package com.example.treeze.task.login;

import com.example.treeze.entity.User;
import com.example.treeze.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    UserRepository userRepository;

    @Override
    public Map<String, Object> login() throws Exception{
        Map<String, Object> result = new HashMap<>();
        List<User> userInfo = userRepository.findAll();
        result.put("userInfo", userInfo);
        result.put("result", "success");
        return result;
    }
}
