package com.example.treeze.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="T_USER")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userSeq;

    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 20, nullable = false)
    private String userPw;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;
}
