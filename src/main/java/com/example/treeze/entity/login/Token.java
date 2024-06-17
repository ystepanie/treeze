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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "refresh_token", length = 100, nullable = false)
    private String refreshToken;

    @Column(name = "token_expiration", length = 10, nullable = false)
    private String tokenExpiration;

}
