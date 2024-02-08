package com.tasktide.userServices.repository;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Repository
public class UserRepository {

    private final IUserRepository iUserRepository;

    @Autowired
    public UserRepository(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


    public User findUserById(String id) {
        return iUserRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String username) {
        return iUserRepository.findUserByUsername(username);
    }

    public List<User> findAllUsers() {
        return (List<User>) iUserRepository.findAll();
    }
    public User createUser(User user) {
        return iUserRepository.save(user);
    }

    public User save(User user) {
        User newUser = findUserById(user.getId());
        if(StringUtils.hasLength(user.getFirstName()))
            newUser.setFirstName(user.getFirstName());
        if(StringUtils.hasLength(user.getLastName()))
            newUser.setLastName(user.getLastName());
        if(StringUtils.hasLength(user.getUsername()))
            newUser.setUsername(user.getUsername());
        if(StringUtils.hasLength(user.getHashedPassword()))
            newUser.setHashedPassword(user.getHashedPassword());
        newUser.setUpdatedDate(Instant.now());
        iUserRepository.save(newUser);
        return newUser;
    }

    public void addNewLogin(String username) {
        User newUser = findUserByUsername(username);
        newUser.setLastLoginDate(Instant.now());
        iUserRepository.save(newUser);
    }

    public User deleteUser(String id) {
        User user = findUserById(id);
        if(user == null) {
            return null;
        }
        iUserRepository.deleteById(id);
        return user;
    }

}
