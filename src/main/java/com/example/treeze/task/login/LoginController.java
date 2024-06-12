package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.response.Response;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    final Logger log = LogManager.getLogger(getClass());

    @PostMapping("/login")
    public ResponseEntity<Response> postLogin(@Valid @RequestBody LoginDto loginDto) throws Exception {
        Response response = loginService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> postSignup(@Valid @RequestBody SignupDto signupDto) throws Exception {
        Response response = loginService.signup(signupDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}