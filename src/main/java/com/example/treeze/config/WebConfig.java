package com.example.treeze.config;

import com.example.treeze.interceptor.TokenInterceptor;
import com.example.treeze.security.AccessJwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    //Spring security 관련 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/v1/login/signup", "/v1/login/login").permitAll()
                    .anyRequest().authenticated())
            .logout(logout -> logout.permitAll());  // 로그아웃도 모든 사용자에게 허용
        return http.build();
    }

    // API 호출 시 도메인 허용
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 모든 도메인을 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Globals globals() {
        return new Globals();
    }

    @Bean
    public AccessJwtToken accessJwtToken(Globals globals) {
        return new AccessJwtToken(globals);
    }

    @Bean
    public TokenInterceptor tokenInterceptor(AccessJwtToken accessJwtToken) {
        return new TokenInterceptor(accessJwtToken);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 토큰 interceptor
        List<String> excludeList = new ArrayList<String>();
        excludeList.add("/v1/login/login");
        excludeList.add("/v1/login/signup");
        excludeList.add("/error");

        // AccessJwtToken 빈 생성
        AccessJwtToken accessJwtToken = accessJwtToken(new Globals());
        // TokenInterceptor 인스턴스 생성
        TokenInterceptor tokenInterceptor = new TokenInterceptor(accessJwtToken);

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/v1/**")
                .excludePathPatterns(excludeList);
    }
}

