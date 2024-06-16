package com.example.treeze.entity.login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="T_USER_REFRESH_TOKEN")
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "refresh_token_seq", allocationSize = 1)
    private int refreshTokenSeq;

    @Column(name = "user_seq", nullable = false)
    private int useSeq;

    @Column(name = "refresh_token", length = 100, nullable = false)
    private String refreshToken;

    @Column(name = "token_expiration", length = 10, nullable = false)
    private String tokenExpiration;

}
