package com.tasktide.authServices.repository.interfaces;

import com.tasktide.authServices.model.User;
import org.springframework.data.repository.CrudRepository;

public interface IAuthRepository extends CrudRepository<User, String> {

    Iterable<User> findUsersByUsername(String username);
}
