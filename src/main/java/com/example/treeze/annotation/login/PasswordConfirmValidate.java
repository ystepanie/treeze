package com.example.treeze.annotation.login;

import com.example.treeze.util.MessageUtil;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordConfrimValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmValidate {
    String message() default MessageUtil.OTHER_PASSWORD;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String passwordField();
    String confirmPasswordField();
}
