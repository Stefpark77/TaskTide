package com.tasktide.authServices.mapper;

import com.tasktide.authServices.controller.model.Request.SignUpRequest;
import com.tasktide.authServices.model.User;
import com.tasktide.authServices.security.model.Role;
import com.tasktide.authServices.service.hashing.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignUpMapper {

    public User mapFromRequest(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setHashedPassword(BCryptPasswordEncoder.hash(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

}
