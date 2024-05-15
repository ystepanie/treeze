package com.example.treeze.security;

import org.springframework.stereotype.Component;

@Component
public class AccessJwtToken {
    private static final String JWT_HEADER_TYPE = "JWT";
    // jwtkey
    private String jwtSecretKey;

//    public JwtBuilder generateToken(User user) {
//        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes("UTF-8"));
//        jwtSecretKey = getValue("jwt.secretKey");
//        return Jwts.builder()
//                .setHeaderParam("type", JWT_HEADER_TYPE)
//                .signWith()
//    }
}
