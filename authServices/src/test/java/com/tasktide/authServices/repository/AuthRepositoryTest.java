package com.tasktide.authServices.repository;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.interfaces.IAuthRepository;
import com.tasktide.authServices.service.AuthService;
import com.tasktide.authServices.service.hashing.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthRepositoryTest {

    @Mock
    private IAuthRepository iAuthRepository;

    @InjectMocks
    private AuthRepository authRepository;

    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    @Test
    public void findUsersByUsername_success()  {

        User user = getUser();
        when(iAuthRepository.findUsersByUsername(USERNAME)).thenReturn(List.of(user));

        List<User> response = authRepository.findUsersByUsername(USERNAME);

        assertNotNull(response);
        assertEquals(response.getFirst(), user);
        verify(iAuthRepository).findUsersByUsername(anyString());
    }

    @Test
    public void findUsersByUsername_exception()   {

        when(iAuthRepository.findUsersByUsername(USERNAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> authRepository.findUsersByUsername(USERNAME));
        verify(iAuthRepository).findUsersByUsername(anyString());
    }

    private User getUser(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setHashedPassword(BCryptPasswordEncoder.hash(PASSWORD));
        return user;
    }
}
