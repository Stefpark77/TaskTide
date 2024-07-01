package com.tasktide.userServices.service;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByUserId(String userId) {
        return userRepository.findUserById(userId);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getUsersByProjectId(String projectId) {
        return userRepository.findUsersByProjectId(projectId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public User addNewUser(User user) {
        return userRepository.createUser(user);
    }

    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public User removeUser(String userId) {
        return userRepository.deleteUser(userId);
    }

}
