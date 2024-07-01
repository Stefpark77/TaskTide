package com.tasktide.authServices.service;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.AuthRepository;
import com.tasktide.authServices.service.hashing.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private AuthService authService;

    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String INCORRECT_PASSWORD = "password123";

    @Test
    public void loginUser_success_notNullUserAndCorrectPassword() {

        User user = getUser();
        when(authRepository.findUsersByUsername(USERNAME)).thenReturn(List.of(user));

        User response = authService.loginUser(USERNAME, PASSWORD);

        assertNotNull(response);
        assertEquals(response, user);
        verify(authRepository).findUsersByUsername(anyString());
    }

    @Test
    public void loginUser_success_notNullUserAndIncorrectPassword() {

        User user = getUser();
        when(authRepository.findUsersByUsername(USERNAME)).thenReturn(List.of(user));

        User response = authService.loginUser(USERNAME, INCORRECT_PASSWORD);

        assertNull(response);
        verify(authRepository).findUsersByUsername(anyString());
    }

    @Test
    public void loginUser_success_nullUser() {

        when(authRepository.findUsersByUsername(USERNAME)).thenReturn(null);

        User response = authService.loginUser(USERNAME, INCORRECT_PASSWORD);

        assertNull(response);
        verify(authRepository).findUsersByUsername(anyString());
    }

    @Test
    public void loginUser_exception() {

        when(authRepository.findUsersByUsername(USERNAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> authService.loginUser(USERNAME, PASSWORD));
        verify(authRepository).findUsersByUsername(anyString());
    }

    private User getUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setHashedPassword(BCryptPasswordEncoder.hash(PASSWORD));
        return user;
    }


}
