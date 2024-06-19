package com.example.treeze.repository;

import com.example.treeze.entity.login.Token;
import com.example.treeze.repository.login.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void 토큰_저장_성공() {
        //given
        Token token = new Token();
        token.setUserSeq(1L);
        token.setRefreshToken("refreshTokenValue");
        token.setTokenExpiration("2024-01-01");
        //when
        Token tokenResult = tokenRepository.save(token);
        //then
        assertNotNull(tokenResult.getUserSeq());
        assertEquals(token.getUserSeq(), tokenResult.getUserSeq());
        assertEquals(token.getRefreshToken(), tokenResult.getRefreshToken());
        assertEquals(token.getTokenExpiration(), tokenResult.getTokenExpiration());
    }

    private static Stream<Arguments> tokenNullArguments() {
        return Stream.of(
                Arguments.of( null, "2024-01-01"),
                Arguments.of( "refreshTokenValue", null)
        );
    }

    @ParameterizedTest
    @MethodSource("tokenNullArguments")
    void 토큰_널값(String refreshToken, String tokenExpiration) {
        //given
        Token failToken = new Token();
        failToken.setUserSeq(1L);
        failToken.setRefreshToken(refreshToken);
        failToken.setTokenExpiration(tokenExpiration);
        // when
        // then
        assertThrows(DataIntegrityViolationException.class, () -> {
            tokenRepository.save(failToken);
        });
    }

}
