package com.example.treeze.dto.login;

import com.example.treeze.annotation.login.PasswordConfirmValidate;
import com.example.treeze.util.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@PasswordConfirmValidate(passwordField = "userPw", confirmPasswordField = "userPwConfirm")
@Builder
public record SignupDto(
        @NotBlank(message = MessageUtil.BLANK_ID)
        @Size(min = 4, max = 15, message = MessageUtil.INVALID_LENGTH_ID)
        String userId,

        @NotBlank(message = MessageUtil.BLANK_PASSWORD)
        @Size(min = 8, max = 15, message = MessageUtil.INVALID_LENGTH_PASSWORD)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = MessageUtil.INVALID_PASSWORD)
        String userPw,

        @NotBlank(message = MessageUtil.BLANK_PASSWORD_CONFIRM)
        String userPwConfirm,

        @NotBlank(message = MessageUtil.BLANK_PHONENUMBER)
        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{4}|\\d{3})-\\d{4}$",
                message = MessageUtil.INVALID_PHONENUMBER)
        String phoneNumber
) {}
