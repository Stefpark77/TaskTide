package com.tasktide.authServices.repository;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.interfaces.IAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

    private final IAuthRepository iAuthRepository;

    public List<User> findUsersByUsername(String username) {
        return (List<User>) iAuthRepository.findUsersByUsername(username);
    }

}
