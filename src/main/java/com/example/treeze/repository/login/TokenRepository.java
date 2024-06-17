package com.example.treeze.repository.login;

import com.example.treeze.entity.login.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // dao 명시. 관련 예외는 dataAccessException으로 일괄 처리됨
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Modifying //jpa에 변경을 가하는 쿼리라고 명시하는 어노테이션
    @Transactional
    @Query(value = "INSERT INTO T_USER_REFRESH_TOKEN(" +
                    "       USER_SEQ" +
                    "       , REFRESH_TOKEN" +
                    "       , TOKEN_EXPIRATION" +
                    "       ) VALUES (" +
                    "       :userSeq" +
                    "       , :refreshToken" +
                    "       , :tokenExpiration" +
                    "       ) ON DUPLICATE KEY UPDATE" +
                    "       REFRESH_TOKEN = :refreshToken" +
                    "       , TOKEN_EXPIRATION = :tokenExpiration",
        nativeQuery = true)
    void upsert(@Param("userSeq") Long userSeq,
                 @Param("refreshToken") String refreshToken,
                 @Param("tokenExpiration") String tokenExpiration);
}
