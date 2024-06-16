package com.example.treeze.repository;

import com.example.treeze.entity.login.Token;
import com.example.treeze.entity.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserIdAndUserPw(String userId, String userPw);

    User findByUserId(String userId);

    User save(User user);

    Token saveRefreshToken(Token token);
}
