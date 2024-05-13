package com.example.treeze.repository;

import com.example.treeze.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserIdAndUserPw(String userId, String userPw);
}
