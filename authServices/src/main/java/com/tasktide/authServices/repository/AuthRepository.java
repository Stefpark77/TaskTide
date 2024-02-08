package com.tasktide.authServices.repository;

import com.tasktide.authServices.model.User;
import com.tasktide.authServices.repository.interfaces.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthRepository {

    private final IAuthRepository iAuthRepository;

    @Autowired
    public AuthRepository(IAuthRepository iAuthRepository) {
        this.iAuthRepository = iAuthRepository;
    }

    public List<User> findUsersByUsername(String username) {
        return (List<User>) iAuthRepository.findUsersByUsername(username);
    }

}
