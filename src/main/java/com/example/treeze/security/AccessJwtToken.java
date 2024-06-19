package com.example.treeze.security;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.util.CalendarUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessJwtToken {
    // jwtkey
    private static final String tokenSubjectName = "treeze";
    private static final String tokenSecretKey = "secretKey";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String VALUE_BEARER = "Bearer";

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
                    .setSubject(tokenSubjectName)
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
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
        String accessToken = ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(ACCESS_TOKEN);

        if (StringUtils.isNotEmpty(accessToken)) {
            accessToken = accessToken.replace(VALUE_BEARER, "").trim();
        }
        return accessToken;
    }

    public boolean isTokenExpired(String accessToken) {
        // TODO 만료일자 로직
        // 1. 토큰을 가져온다.
        // 2. 서버에서 저장하는 secretKey를 가져온다.
        // 3. secretKey로 accessToken을 해석한다.(claim객채)
        // 4. claim객체에서 만료일시를 가져온다.
    }

}
