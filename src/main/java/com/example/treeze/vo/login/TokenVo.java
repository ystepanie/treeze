package com.example.treeze.vo.login;

public record TokenVo(
    String accessToken,
    String refreshToken,
    String expiration
) {
}
