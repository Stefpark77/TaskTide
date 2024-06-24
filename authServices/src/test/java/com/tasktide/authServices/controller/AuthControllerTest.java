package com.tasktide.authServices.controller;

import com.tasktide.authServices.mapper.SignUpMapper;
import com.tasktide.authServices.publisher.RabbitMQProducer;
import com.tasktide.authServices.security.JwtIssuer;
import com.tasktide.authServices.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

    @Test
    public void signUp_success() throws Exception {

    }
}
