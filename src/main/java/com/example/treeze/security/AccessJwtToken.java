package com.example.treeze.security;

import com.example.treeze.config.Globals;
import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.exception.SpringSecurityException;
import com.example.treeze.util.CalendarUtil;
import com.example.treeze.util.MessageUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccessJwtToken {
    // jwtkey
    private static final String HEADER_KEY_ACCESS_TOKEN = "x-token";
    private static final String VALUE_BEARER = "Bearer";

    private final Globals globals;

    public String generateAccessToken(LoginDto user) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "JWT");
            headers.put("alg", "HS256");

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.userId());
            claims.put("dateTime", CalendarUtil.getCurrentDate());

            int AddTokenTime = 60000; //1hour
            Date expiredTokenDate = new Date();
            expiredTokenDate.setTime(expiredTokenDate.getTime() + AddTokenTime);

            return Jwts.builder()
                    .setHeader(headers)
                    .setClaims(claims)
                    .setExpiration(expiredTokenDate)
                    .setSubject(globals.getJwtSubjectName())
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, globals.getJwtSecretKey())
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        String accessToken = ((ServletRequestAttributes) requestAttributes)
                .getRequest()
                .getHeader(HEADER_KEY_ACCESS_TOKEN);

        if (StringUtils.isNotEmpty(accessToken)) {
            accessToken = accessToken.replace(VALUE_BEARER, "").trim();
        }
        return accessToken;
    }

    public boolean isTokenValidation(String accessToken) {
        final Claims claims = getAllClaimsFromToken(accessToken);
        System.out.println("claims : " + claims);
        if(claims == null) {
            return false;
        }
        return true;
    }

    private Claims getAllClaimsFromToken(String token) {
        // 1. 토큰을 가져온다.
        // 2. 서버에서 저장하는 secretKey를 가져온다.
        // 3. secretKey로 accessToken을 해석한다.(claim객체)
        try {
            SecretKey secretKey;
            secretKey = Keys.hmacShaKeyFor(globals.getJwtSecretKey().getBytes("UTF-8"));
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (WeakKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SpringSecurityException(MessageUtil.TOKEN_VALIDATION, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new SpringSecurityException(MessageUtil.TOKEN_VALIDATION, HttpStatus.UNAUTHORIZED);
        }
    }

}
