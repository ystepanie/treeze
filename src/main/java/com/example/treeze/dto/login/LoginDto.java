package com.example.treeze.dto.login;


import com.example.treeze.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = "아이디를 입력해 주세요.")
        @Size(max = 15, message = "아이디가 너무 깁니다.")
        String user_id,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Size(max = 15, message = "비밀번호가 너무 깁니다.")
        String user_pw
){
    public LoginDto(User user) {
        this(user.getUser_id(), user.getUser_pw());
    }
}
