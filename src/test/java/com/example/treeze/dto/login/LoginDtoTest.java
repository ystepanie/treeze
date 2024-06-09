package com.example.treeze.dto.login;

import com.example.treeze.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void loginValidation_success() {
        LoginDto loginDto  = new LoginDto("validId", "1");

        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        assertTrue(violations.isEmpty(), MessageUtil.LOGIN_SUCCESS);
    }
}