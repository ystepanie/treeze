package com.example.treeze.repository.login;

import com.example.treeze.entity.login.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
}
