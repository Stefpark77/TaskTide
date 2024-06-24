package com.tasktide.projectServices.repository;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.model.ProjectUserId;
import com.tasktide.projectServices.repository.interfaces.IProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class ProjectUserRepository {
    private final IProjectUserRepository iProjectUserRepository;

    public List<ProjectUser> findProjectUsersForUserId(String userId) {
        return (List<ProjectUser>) iProjectUserRepository.findProjectUserByUserIdContains(userId);
    }

    public List<ProjectUser> findAllProjectUsers() {
        return (List<ProjectUser>) iProjectUserRepository.findAll();
    }

    public ProjectUser createProjectUser(ProjectUser projectUser) {
        return iProjectUserRepository.save(projectUser);
    }

    public ProjectUser deleteProjectUser(String userId, String projectId) {
        ProjectUser dependency = findProjectUsersForUserId(userId).stream()
                .filter(x -> Objects.equals(x.getProjectId(), projectId))
                .findFirst().orElse(null);
        if (dependency == null) {
            return null;
        }
        iProjectUserRepository.deleteById(new ProjectUserId(userId, projectId));
        return dependency;
    }

    public void deleteAllProjectUsersForProjectId(String projectId) {
        List<ProjectUser> projectUsersToDelete = (List<ProjectUser>) iProjectUserRepository.findProjectUserByProjectIdContains(projectId);
        for (ProjectUser projectUser : projectUsersToDelete) {
            iProjectUserRepository.deleteById(new ProjectUserId(projectUser.getUserId(), projectUser.getProjectId()));
        }
    }

}
