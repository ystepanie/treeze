package com.example.treeze.security;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.util.CalendarUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessJwtToken {
    // jwtkey
    private static final String tokenSubjectName = "treeze";
    private static final String tokenSecretKey = "secretKey";

    public String generateAccessToken(LoginDto user) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "JWT");
            headers.put("alg", "HS512");

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

}
