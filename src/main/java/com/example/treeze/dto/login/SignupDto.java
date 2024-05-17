package com.example.treeze.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupDto(
        @NotBlank(message = "아이디를 입력해 주세요.")
        @Size(max = 15, message = "아이디가 너무 깁니다.")
        String userId,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Size(max = 15, message = "비밀번호가 너무 깁니다.")
        String userPw,

        @NotBlank(message = "비밀번호 확인을 입력해 주세요.")
        String userPwConfirm,

        @NotBlank(message = "휴대폰번호를 입력해 주세요.")
        @Size(max = 14, message = "휴대폰번호가 너무 깁니다.")
        String phoneNumber
){}
