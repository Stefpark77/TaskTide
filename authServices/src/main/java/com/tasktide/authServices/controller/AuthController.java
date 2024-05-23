package com.tasktide.authServices.controller;

import com.tasktide.authServices.controller.model.Request.LogInRequest;
import com.tasktide.authServices.controller.model.Request.SignUpRequest;
import com.tasktide.authServices.controller.model.Response.LoginResponse;
import com.tasktide.authServices.mapper.SignUpMapper;
import com.tasktide.authServices.model.User;
import com.tasktide.authServices.publisher.RabbitMQProducer;
import com.tasktide.authServices.security.JwtIssuer;
import com.tasktide.authServices.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtIssuer jwtIssuer;
    private final SignUpMapper signUpMapper;
    private final AuthService authService;
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping(value = "/sign-up")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        rabbitMQProducer.sendJsonMessage(signUpMapper.mapFromRequest(signUpRequest));
    }

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LogInRequest logInRequest) {
        User user = authService.loginUser(logInRequest.getUsername(), logInRequest.getPassword());

        if (user == null) {
            return null;
        }

        return LoginResponse.builder()
                .token(jwtIssuer.issue(user.getId(), user.getUsername(), user.getRole()))
                .userId(user.getId())
                .build();
    }

}
