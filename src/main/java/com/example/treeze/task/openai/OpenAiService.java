package com.example.treeze.task.openai;

import com.example.treeze.dto.openai.PromptDto;
import com.example.treeze.response.Response;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface OpenAiService {
    public Response chatGpt(PromptDto promptDto) throws Exception;

    public HttpRequest requestGptApi(PromptDto promptDto) throws Exception;

    public HttpResponse responseGptApi(HttpRequest request) throws Exception;
}
