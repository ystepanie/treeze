package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Map<String, Object> firstController(@RequestBody LoginDto loginDto) throws Exception {
        Map<String, Object> result = loginService.login(loginDto);
        return result;
    }
}