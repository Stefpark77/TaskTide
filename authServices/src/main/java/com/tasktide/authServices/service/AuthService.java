package com.tasktide.authServices.service;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.AuthRepository;
import com.tasktide.authServices.service.hashing.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AuthService {
    AuthRepository authRepository;
    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User getUserByUsername(String username) {
        List<User> userList = authRepository.findUsersByUsername(username);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }

    public User loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if(!BCryptPasswordEncoder.match(password, user.getHashedPassword())){
            return null;
        }
        return user;
    }

}
