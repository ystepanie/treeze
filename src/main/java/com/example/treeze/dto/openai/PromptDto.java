package com.example.treeze.dto.openai;

import com.example.treeze.util.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PromptDto(
        @NotBlank(message = MessageUtil.BLANK_PRMOPT)
        String prompt
) {}
