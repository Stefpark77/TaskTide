package com.tasktide.userServices.repository;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.interfaces.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserRepository userRepository;

    private static final String USERNAME = "username";
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";


    @Test
    public void findUserById_success() {

        User user = getUser();
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        User response = userRepository.findUserById(USER_ID);

        assertNotNull(response);
        assertEquals(response, user);
        verify(iUserRepository).findById(USER_ID);
    }

    @Test
    public void findUserById_exception() {

        when(iUserRepository.findById(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.findUserById(USER_ID));
        verify(iUserRepository).findById(USER_ID);
    }

    @Test
    public void findUserByUsername_success() {

        User user = getUser();
        when(iUserRepository.findUserByUsername(USERNAME)).thenReturn(user);

        User response = userRepository.findUserByUsername(USERNAME);

        assertNotNull(response);
        assertEquals(response, user);
        verify(iUserRepository).findUserByUsername(USERNAME);
    }


    @Test
    public void findUserByUsername_exception() {

        when(iUserRepository.findUserByUsername(USERNAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.findUserByUsername(USERNAME));
        verify(iUserRepository).findUserByUsername(anyString());
    }


    @Test
    public void findUsersByProjectId_success() {

        User user = getUser();
        when(iUserRepository.findUsersByProjectId(PROJECT_ID)).thenReturn(List.of(user));

        List<User> response = userRepository.findUsersByProjectId(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), user);
        verify(iUserRepository).findUsersByProjectId(PROJECT_ID);
    }

    @Test
    public void findUsersByProjectId_exception() {

        when(iUserRepository.findUsersByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.findUsersByProjectId(PROJECT_ID));
        verify(iUserRepository).findUsersByProjectId(PROJECT_ID);
    }


    @Test
    public void findAllUsers_success() {

        User user = getUser();
        when(iUserRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userRepository.findAllUsers();

        assertNotNull(response);
        assertEquals(response.getFirst(), user);
        verify(iUserRepository).findAll();
    }

    @Test
    public void findAllUsers_exception() {

        when(iUserRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.findAllUsers());
        verify(iUserRepository).findAll();
    }


    @Test
    public void createUser_success() {

        User user = getUser();
        when(iUserRepository.save(any())).thenReturn(user);

        User response = userRepository.createUser(user);

        assertNotNull(response);
        assertEquals(response, user);
        verify(iUserRepository).save(any());
    }

    @Test
    public void createUser_exception() {

        when(iUserRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.createUser(getUser()));
        verify(iUserRepository).save(any());
    }


    @Test
    public void updateUser_success() {

        User user = getUser();
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(iUserRepository.save(any())).thenReturn(user);

        User response = userRepository.updateUser(user);

        assertNotNull(response);
        assertEquals(response, user);
        verify(iUserRepository).findById(USER_ID);
        verify(iUserRepository).save(any());
    }

    @Test
    public void updateUser_exception() {
        User user = getUser();
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(iUserRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userRepository.updateUser(getUser()));
        verify(iUserRepository).findById(USER_ID);
        verify(iUserRepository).save(any());
    }


    @Test
    public void deleteUser_success() {

        User user = getUser();
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        User response = userRepository.deleteUser(USER_ID);

        assertNotNull(response);
        assertEquals(response, user);
        verify(iUserRepository).findById(USER_ID);
        verify(iUserRepository).deleteById(USER_ID);
    }

    @Test
    public void deleteUser_success_nullUser() {
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.empty());

        User response =  userRepository.deleteUser(USER_ID);

        assertNull(response);
        verify(iUserRepository).findById(USER_ID);
        verify(iUserRepository, times(0)).deleteById(USER_ID);
    }

    @Test
    public void deleteUser_exception() {
        User user = getUser();
        when(iUserRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        doThrow(RuntimeException.class).when(iUserRepository).deleteById(USER_ID);

        assertThrows(RuntimeException.class, () -> userRepository.deleteUser(USER_ID));
        verify(iUserRepository).findById(USER_ID);
        verify(iUserRepository).deleteById(USER_ID);
    }


    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        return user;
    }
}
