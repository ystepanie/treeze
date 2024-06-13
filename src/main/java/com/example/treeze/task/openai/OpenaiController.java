package com.example.treeze.task.openai;

import com.example.treeze.dto.openai.PromptDto;
import com.example.treeze.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chatTreeze")
@RequiredArgsConstructor
public class OpenaiController {
    private final OpenaiService openaiService;

    @PostMapping("/qna")
    public ResponseEntity<Response> postChatGpt(PromptDto promptDto) throws Exception {
        Response response = openaiService.chatGpt(promptDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
