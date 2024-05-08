package com.example.treeze.task.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/first")
    public Map<String, Object> firstController() throws Exception {
        return loginService.login();
    }
}