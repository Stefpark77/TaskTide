package com.tasktide.userServices.repository.interfaces;

import com.tasktide.userServices.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface IUserRepository extends CrudRepository<User, String> {

    User findUserByUsername(String username);

    @Query("select u from User u inner join ProjectUser pu on pu.userId = u.id where pu.projectId= :projectId")
    List<User> findUsersByProjectId(String projectId);
}
