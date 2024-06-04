package com.example.treeze.task.login;

import com.example.treeze.dto.login.LoginDto;
import com.example.treeze.dto.login.SignupDto;
import com.example.treeze.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@WithMockUser
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LoginServiceImpl loginService;

    private LoginDto validLoginDto;
    private LoginDto invalidLoginDto;
    private SignupDto validSignupDto;

    @BeforeEach
    public void setUp() {
        validLoginDto = new LoginDto("ystepanie", "Ystep950830!");
        invalidLoginDto = new LoginDto("id", "short");
        validSignupDto = new SignupDto("test123", "Testtest123!", "Testtest123!", "010-1234-5678");
    }

    @Test
    void 로그인_성공() throws Exception {
        // given
        String validRequestBody = objectMapper.writeValueAsString(validLoginDto);
        // then
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestBody))
                        .andExpect(status().isOk());
    }

    @Test
    void 로그인_유저존재하지않음() throws Exception {
        // given
        String invalidRequestBody = objectMapper.writeValueAsString(invalidLoginDto);

        // when
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
        // then
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0].message", is(MessageUtil.USER_NOT_EXIST)));
    }

    @Test
    void 회원가입_성공() throws Exception {
        // given
        String validRequestBody = objectMapper.writeValueAsString(validSignupDto);

        // when
        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestBody))
                .andExpect(status().isOk());
        // then
    }
}