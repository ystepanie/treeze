package com.example.treeze.vo.login;

import lombok.Builder;

@Builder
public record UserVo(
    int userSeq,
    String userId,
    String userPw
) {}
