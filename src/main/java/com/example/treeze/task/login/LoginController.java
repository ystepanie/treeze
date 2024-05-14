package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.response.Response;
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
    public Response loginController(@RequestBody LoginDto loginDto) throws Exception {
        Map<String, Object> result = loginService.login(loginDto);
        String status = result.get("status").toString();
        String message = result.get("message").toString();
        Object data = result.get("data");
        return new Response(status, message, data);
    }
}