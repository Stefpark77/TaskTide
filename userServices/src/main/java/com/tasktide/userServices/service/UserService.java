package com.tasktide.userServices.service;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUserId(String userId) {
        return userRepository.findUserById(userId);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public List<User> getUserByProjectId(String projectId) {
        return userRepository.findUserByProjectId(projectId);
    }
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }
    public User addNewUser(User user) {
        return userRepository.createUser(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User removeUser(String userId) {
        return userRepository.deleteUser(userId);
    }

}
