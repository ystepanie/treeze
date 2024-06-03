package com.example.treeze.dto.login;


import com.example.treeze.util.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = MessageUtil.BLANK_ID)
        @Size(max = 15, min = 4, message = MessageUtil.INVALID_LENGTH_ID)
        String userId,

        @NotBlank(message = MessageUtil.BLANK_PASSWORD)
        @Size(max = 20, min = 8, message = MessageUtil.INVALID_LENGTH_PASSWORD)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
                , message = MessageUtil.INVALID_PASSWORD)
        String userPw
){}
