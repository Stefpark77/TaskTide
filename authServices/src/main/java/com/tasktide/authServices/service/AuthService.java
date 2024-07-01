package com.tasktide.authServices.service;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.AuthRepository;
import com.tasktide.authServices.service.hashing.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public User loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null || !BCryptPasswordEncoder.match(password, user.getHashedPassword())) {
            return null;
        }
        return user;
    }

    private User getUserByUsername(String username) {
        List<User> userList = authRepository.findUsersByUsername(username);
        return CollectionUtils.isEmpty(userList) ? null : userList.getFirst();
    }


}
