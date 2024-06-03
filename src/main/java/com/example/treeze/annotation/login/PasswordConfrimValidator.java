package com.example.treeze.annotation.login;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordConfrimValidator implements ConstraintValidator<PasswordConfirmValidate, Object> {
    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordConfirmValidate constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object userPassword = new BeanWrapperImpl(value).getPropertyValue(passwordField);
            Object userPasswordConfirm = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);
            return userPassword.equals(userPasswordConfirm);
        } catch (Exception e) {
            return false;
        }
    }
}
