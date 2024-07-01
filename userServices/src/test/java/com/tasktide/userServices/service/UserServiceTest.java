package com.tasktide.userServices.service;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String USERNAME = "username";
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";


    @Test
    public void getUserByUserId_success() {

        User user = getUser();
        when(userRepository.findUserById(USER_ID)).thenReturn(user);

        User response = userService.getUserByUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response, user);
        verify(userRepository).findUserById(USER_ID);
    }

    @Test
    public void getUserByUserId_exception() {

        when(userRepository.findUserById(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getUserByUserId(USER_ID));
        verify(userRepository).findUserById(USER_ID);
    }

    @Test
    public void getUserByUsername_success() {

        User user = getUser();
        when(userRepository.findUserByUsername(USERNAME)).thenReturn(user);

        User response = userService.getUserByUsername(USERNAME);

        assertNotNull(response);
        assertEquals(response, user);
        verify(userRepository).findUserByUsername(USERNAME);
    }


    @Test
    public void getUserByUsername_exception() {

        when(userRepository.findUserByUsername(USERNAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getUserByUsername(USERNAME));
        verify(userRepository).findUserByUsername(anyString());
    }


    @Test
    public void getUsersByProjectId_success() {

        User user = getUser();
        when(userRepository.findUsersByProjectId(PROJECT_ID)).thenReturn(List.of(user));

        List<User> response = userService.getUsersByProjectId(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), user);
        verify(userRepository).findUsersByProjectId(PROJECT_ID);
    }

    @Test
    public void getUsersByProjectId_exception() {

        when(userRepository.findUsersByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getUsersByProjectId(PROJECT_ID));
        verify(userRepository).findUsersByProjectId(PROJECT_ID);
    }


    @Test
    public void getAllUsers_success() {

        User user = getUser();
        when(userRepository.findAllUsers()).thenReturn(List.of(user));

        List<User> response = userService.getAllUsers();

        assertNotNull(response);
        assertEquals(response.getFirst(), user);
        verify(userRepository).findAllUsers();
    }

    @Test
    public void getAllUsers_exception() {

        when(userRepository.findAllUsers()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getAllUsers());
        verify(userRepository).findAllUsers();
    }


    @Test
    public void addNewUser_success() {

        User user = getUser();
        when(userRepository.createUser(any())).thenReturn(user);

        User response = userService.addNewUser(user);

        assertNotNull(response);
        assertEquals(response, user);
        verify(userRepository).createUser(any());
    }

    @Test
    public void addNewUser_exception() {

        when(userRepository.createUser(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.addNewUser(getUser()));
        verify(userRepository).createUser(any());
    }


    @Test
    public void updateUser_success() {

        User user = getUser();
        when(userRepository.updateUser(any())).thenReturn(user);

        User response = userService.updateUser(user);

        assertNotNull(response);
        assertEquals(response, user);
        verify(userRepository).updateUser(any());
    }

    @Test
    public void updateUser_exception() {
        when(userRepository.updateUser(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.updateUser(getUser()));
        verify(userRepository).updateUser(any());
    }


    @Test
    public void deleteUser_success() {

        User user = getUser();
        when(userRepository.deleteUser(USER_ID)).thenReturn(user);

        User response = userService.removeUser(USER_ID);

        assertNotNull(response);
        assertEquals(response, user);
        verify(userRepository).deleteUser(USER_ID);
    }

    @Test
    public void deleteUser_exception() {
        doThrow(RuntimeException.class).when(userRepository).deleteUser(USER_ID);

        assertThrows(RuntimeException.class, () -> userService.removeUser(USER_ID));
        verify(userRepository).deleteUser(USER_ID);
    }


    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        return user;
    }


}
