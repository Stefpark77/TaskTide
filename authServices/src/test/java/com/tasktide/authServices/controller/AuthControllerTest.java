package com.tasktide.authServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktide.authServices.controller.model.Request.LogInRequest;
import com.tasktide.authServices.controller.model.Request.SignUpRequest;
import com.tasktide.authServices.controller.model.Response.LoginResponse;
import com.tasktide.authServices.mapper.SignUpMapper;
import com.tasktide.authServices.model.User;
import com.tasktide.authServices.publisher.RabbitMQProducer;
import com.tasktide.authServices.security.JwtIssuer;
import com.tasktide.authServices.security.model.Role;
import com.tasktide.authServices.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private JwtIssuer jwtIssuer;
    @Mock
    private SignUpMapper signUpMapper;
    @Mock
    private AuthService authService;
    @Mock
    private RabbitMQProducer rabbitMQProducer;
    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }

    @Test
    public void signUp_success() throws Exception {

        User user = new User();
        when(signUpMapper.mapFromRequest(any())).thenReturn(user);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSignUpRequest()))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();
        verify(signUpMapper).mapFromRequest(any());
        verify(rabbitMQProducer).sendJsonMessage(user);
    }


    @Test
    public void signUp_exception() throws Exception {

        when(signUpMapper.mapFromRequest(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSignUpRequest()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(signUpMapper).mapFromRequest(any());
        verifyNoInteractions(rabbitMQProducer);
    }

    @Test
    public void login_success_notNullUser() throws Exception {

        User user = new User();
        user.setId("userId");
        user.setRole(Role.USER);
        when(authService.loginUser(anyString(), anyString())).thenReturn(user);
        when(jwtIssuer.issue(eq("userId"), any(), eq(Role.USER))).thenReturn("token");


        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLoginRequest()))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        LoginResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getUserId(), "userId");
        assertEquals(response.getToken(), "token");

        verify(authService).loginUser(anyString(), anyString());
        verify(jwtIssuer).issue(eq("userId"), any(), eq(Role.USER));
    }

    @Test
    public void login_success_nullUser() throws Exception {
        when(authService.loginUser(anyString(), anyString())).thenReturn(null);


        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLoginRequest()))
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(StringUtils.isBlank(result.getResponse().getContentAsString()));

        verify(authService).loginUser(anyString(), anyString());
        verifyNoInteractions(jwtIssuer);

    }


    @Test
    public void login_exception() throws Exception {
        when(authService.loginUser(anyString(), anyString())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLoginRequest()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();

        verify(authService).loginUser(anyString(), anyString());
        verifyNoInteractions(jwtIssuer);
    }


    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(AuthServicesAdvice.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new AuthServicesAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    private SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("email");
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstName("first");
        signUpRequest.setLastName("last");

        return signUpRequest;
    }


    private LogInRequest getLoginRequest() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUsername("username");
        logInRequest.setPassword("password");

        return logInRequest;
    }
}
