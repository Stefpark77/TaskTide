package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
    ProjectRepository projectRepository;
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
        return projectRepository.save(project);
    }

    public Project removeProject(String projectId) {
        return projectRepository.deleteProject(projectId);
    }
}
