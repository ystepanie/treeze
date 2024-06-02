package com.example.treeze.repository;

import com.example.treeze.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);
    }

    @Test
    void findByUserIdAndUserPw_success() {
        //given
        User user = new User();
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        String userId = "id1";
        String userPw = "Testtest123!";
        //when
        User findUser = userRepository.findByUserIdAndUserPw(userId, userPw);
        //then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo(userId);
        assertThat(findUser.getUserPw()).isEqualTo(userPw);
    }

    @Test
    void findByUserIdAndUserPw_notFindUser() {
        //given
        User user = new User();
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        String userId = "id2";
        String userPw = "Testtest123!";
        //when
        User findUser = userRepository.findByUserIdAndUserPw(userId, userPw);
        //then
        assertThat(findUser).isNull();
    }

    @Test
    void findByUserId_success() {
        //given
        User user = new User();
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        String userId = "id1";
        //when
        User findUser = userRepository.findByUserId(userId);
        //then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo(userId);
    }

    @Test
    void findByUserId_notFindUser() {
        //given
        User user = new User();
        user.setUserId("id1");
        user.setUserPw("Testtest123!");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        String userId = "id2";
        //when
        User findUser = userRepository.findByUserId(userId);
        //then
        assertThat(findUser).isNull();
    }

    @Test
    void save_success() {
        //given
        User saveUser = new User();
        saveUser.setUserId("saveUser");
        saveUser.setUserPw("saveUserPw1!");
        saveUser.setPhoneNumber("010-1234-5678");
        //when
        saveUser = userRepository.save(saveUser);
        //then
        assertThat(saveUser.getUserId()).isEqualTo("saveUser");
        assertThat(saveUser.getUserPw()).isEqualTo("saveUserPw1!");
        assertThat(saveUser.getPhoneNumber()).isEqualTo("010-1234-5678");
    }
}