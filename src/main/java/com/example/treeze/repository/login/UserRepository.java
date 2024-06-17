package com.example.treeze.repository.login;

import com.example.treeze.entity.login.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIdAndUserPw(String userId, String userPw);

    User findByUserId(String userId);

    User save(User user);
}
