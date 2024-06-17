package com.example.treeze.vo.login;

import lombok.Builder;

@Builder
public record UserVo(
    Long userSeq,
    String userId,
    String userPw
) {}
