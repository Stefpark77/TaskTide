package com.tasktide.projectServices.repository.interfaces;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.model.ProjectUserId;
import org.springframework.data.repository.CrudRepository;

public interface IProjectUserRepository extends CrudRepository<ProjectUser, ProjectUserId> {
    Iterable<ProjectUser> findProjectUserByUserIdContains(String userId);
}
