package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.repository.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUserService {
    private final ProjectUserRepository projectUserRepository;

    public List<ProjectUser> getProjectUsersByUserId(String userId) {
        return projectUserRepository.findProjectUsersForUserId(userId);
    }

    public List<ProjectUser> getAllProjectUsers() {
        return projectUserRepository.findAllProjectUsers();
    }

    public ProjectUser addNewProjectUser(ProjectUser dependency) {
        return projectUserRepository.createProjectUser(dependency);
    }

    public ProjectUser removeProjectUser(String userId, String projectId) {
        return projectUserRepository.deleteProjectUser(userId, projectId);
    }

    public void removeAllProjectUsersForProjectId(String projectId) {
        projectUserRepository.deleteAllProjectUsersForProjectId(projectId);
    }
}

