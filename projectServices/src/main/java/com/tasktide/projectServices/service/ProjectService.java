package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project getProjectByProjectId(String projectId) {
        return projectRepository.findProjectById(projectId);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllProjects();
    }

    public Project addNewProject(Project project) {
        return projectRepository.createProject(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.updateProject(project);
    }

    public Project removeProject(String projectId) {
        return projectRepository.deleteProject(projectId);
    }
}
