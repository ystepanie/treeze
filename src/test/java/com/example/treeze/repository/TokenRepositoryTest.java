package com.example.treeze.repository;

import com.example.treeze.entity.login.Token;
import com.example.treeze.repository.login.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    private Token token;

    @BeforeEach
    void setUp() {}

    @Test
    void 토큰_저장_성공() {
        //given
        Token token = new Token();
        token.setUseSeq(1);
        token.setRefreshToken("refreshTokenValue");
        token.setTokenExpiration("2024-01-01");
        //when
        Token tokenResult = tokenRepository.save(token);
        //then
        assertNotNull(tokenResult.getRefreshTokenSeq());
        assertEquals(token.getRefreshToken(), tokenResult.getRefreshToken());
    }

    @Test
    void 토큰_존재하지않는_유저seq() {
        //given
        Token failToken = new Token();
        failToken.setUseSeq(0);
        failToken.setRefreshToken("refreshTokenValue");
        failToken.setTokenExpiration("2024-01-01");
        // when
        // then
        assertThrows(DataIntegrityViolationException.class, () -> {
            tokenRepository.save(failToken);
        });
    }

}
