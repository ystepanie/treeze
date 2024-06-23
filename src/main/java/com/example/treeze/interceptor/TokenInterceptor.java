package com.example.treeze.interceptor;

import com.example.treeze.exception.SpringSecurityException;
import com.example.treeze.security.AccessJwtToken;
import com.example.treeze.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AccessJwtToken accessJwtToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = accessJwtToken.getToken();
        // 토큰 만료 여부 확인
        IsAccessTokenExist(accessToken);
        isAccessTokenValidation(accessToken);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean IsAccessTokenExist(String accessToken) throws Exception {
        if (StringUtils.isEmpty(accessToken)) { // token 빈 값 확인
            log.error("accessJwtToken null!!!!!!");
            throw new SpringSecurityException(MessageUtil.TOKEN_NOT_EXIST, HttpStatus.INTERNAL_SERVER_ERROR);
        } else { // 빈 값도 아닐 떄
            return true;
        }
    }

    private boolean isAccessTokenValidation(String accessToken) throws Exception {
        if(accessJwtToken.isTokenValidation(accessToken)) { // access token 만료
            log.error("accessJwtToken validation null!!!!!!");
            return false;
        } else {
            return true;
        }
    }
}
