package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.repository.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectUserService {
    private ProjectUserRepository projectUserRepository;

    @Autowired
    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

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
}

