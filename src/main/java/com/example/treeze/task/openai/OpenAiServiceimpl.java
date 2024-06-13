package com.example.treeze.task.openai;

import com.example.treeze.dto.openai.PromptDto;
import com.example.treeze.exception.BadRequestException;
import com.example.treeze.response.Response;
import com.example.treeze.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class OpenAiServiceimpl implements OpenaiService {

    private final HttpClient httpClient;

    @Value("${openai.api.key}")
    private final String apiKey;

    @Value("${openai.api.url}")
    private final String apiUrl;

    @Override
    public Response chatGpt(PromptDto promptDto) throws Exception {
        HttpRequest gptRequest = requestGptApi(promptDto);
        HttpResponse gptResponse = responseGptApi(gptRequest);

        if (gptResponse.statusCode() == 200) {
            return new Response("success", MessageUtil.OPENAI_SUCCESS, gptResponse.body());
        } else {
            throw new BadRequestException(MessageUtil.OPENAI_FAILED);
        }
    }

    @Override
    public HttpRequest requestGptApi(PromptDto promptDto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", promptDto.prompt());
        requestBody.put("max_tokens", 50);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        return request;
    }

    @Override
    public HttpResponse responseGptApi(HttpRequest request) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
