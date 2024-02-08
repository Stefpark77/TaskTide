package com.tasktide.userServices.repository.interfaces;

import com.tasktide.userServices.model.User;
import org.springframework.data.repository.CrudRepository;


public interface IUserRepository extends CrudRepository<User, String> {

    User findUserByUsername(String username);
}
