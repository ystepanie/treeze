package com.example.treeze.interceptor;

import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AccessJwtToken accessJwtToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = accessJwtToken.getToken();

        if (StringUtils.isEmpty(accessToken)) {
            log.error("access token 없음!!!!!");
            throw new AuthenticationException(MessageUtil.TOKEN_NOT_EXIST); // 토큰 미존재 알림
        }

        // 토큰 만료 여부 확인
        boolean isToken = accessTokenExpiredCheck(request, response);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean accessTokenExpiredCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String accessToken = accessJwtToken.getToken();

        if (StringUtils.isEmpty(accessToken)) { // token 빈 값 확인
            log.error("accessJwtToken 정보 불일치!!!!!!");
            throw new AuthenticationException(MessageUtil.TOKEN_NOT_EXIST);
        } else if(accessJwtToken.isTokenExpired(accessToken)) { // access token 만료
            log.error("accessJwtToken 기간 만료!!!!!!");
            throw new AuthenticationException(MessageUtil.TOKEN_EXPIRATION);
        } else { // 빈 값도 아니고, 기간 만료도 아닐때
            return true;
        }

    }
}
