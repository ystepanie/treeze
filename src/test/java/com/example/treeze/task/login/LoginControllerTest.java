package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.exception.BadRequestException;
import com.example.treeze.response.ErrorResponse;
import com.example.treeze.response.Response;
import com.example.treeze.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@WithMockUser
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginServiceImpl loginService;

    private static final String successStatus = "success";
    private static final String failedStatus = "failed";

    @Test
    void 로그인_성공() throws Exception {
        // given
        LoginDto validLoginDto = new LoginDto("validUser", "validPw1!");
        String validRequestBody = objectMapper.writeValueAsString(validLoginDto);
        Response successResponse = new Response(successStatus, MessageUtil.LOGIN_SUCCESS, "data");
        when(loginService.login(validLoginDto)).thenReturn(successResponse);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/login/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestBody))
                .andExpect(status().isOk())
                .andReturn();
        // then
        String jsonResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Response result = objectMapper.readValue(jsonResponse, Response.class);
        assertThat(result.status()).isEqualTo(successStatus);
        assertThat(result.message()).isEqualTo(MessageUtil.LOGIN_SUCCESS);
    }

    @Test
    void 로그인_유저존재하지않음() throws Exception {
        // given
        LoginDto invalidLoginDto = new LoginDto("invalidUser", "invalidPw1!");
        String validRequestBody = objectMapper.writeValueAsString(invalidLoginDto);
        when(loginService.login(invalidLoginDto)).thenThrow(new BadRequestException(MessageUtil.USER_NOT_EXIST));

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/login/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse result = objectMapper.readValue(jsonResponse, ErrorResponse.class);
        assertThat(result.status()).isEqualTo(failedStatus);
        assertThat(result.errorMessage()).isEqualTo(MessageUtil.USER_NOT_EXIST);
    }

    @Test
    void 회원가입_성공() throws Exception {
        // given
        SignupDto validSignupDto = new SignupDto("test1", "Test123!", "Test123!",
                "010-1234-5678");
        String validRequestBody = objectMapper.writeValueAsString(validSignupDto);
        Response successResponse = new Response(successStatus, MessageUtil.SIGNUP_SUCCESS);
        when(loginService.signup(validSignupDto)).thenReturn(successResponse);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/login/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestBody))
                .andExpect(status().isOk())
                .andReturn();
        // then
        String jsonResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Response result = objectMapper.readValue(jsonResponse, Response.class);
        assertThat(result.message()).isEqualTo(MessageUtil.SIGNUP_SUCCESS);
    }

    @Test
    void 회원가입_동일아이디() throws Exception {
        // given
        SignupDto invalidSignupDto = new SignupDto("test1", "Test123!", "Test123!",
                "010-1234-5678");
        String invalidRequestBody = objectMapper.writeValueAsString(invalidSignupDto);
        Response failedResponse = new Response(failedStatus, MessageUtil.USER_ALREADY_EXIST);
        when(loginService.signup(invalidSignupDto)).thenReturn(failedResponse);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/login/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isOk())
                .andReturn();
        // then
        String jsonResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Response result = objectMapper.readValue(jsonResponse, Response.class);
        assertThat(result.message()).isEqualTo(MessageUtil.USER_ALREADY_EXIST);
    }
}