package com.tasktide.projectServices.repository;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.repository.interfaces.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ProjectRepository {
    private final IProjectRepository iProjectRepository;

    public Project findProjectById(String id) {
        return iProjectRepository.findById(id).orElse(null);
    }

    public List<Project> findAllProjects() {
        return (List<Project>) iProjectRepository.findAll();
    }

    public Project createProject(Project project) {
        return iProjectRepository.save(project);
    }

    public Project save(Project project) {
        Project newProject = findProjectById(project.getId());
        if (StringUtils.hasLength(project.getName()))
            newProject.setName(project.getName());
        if (StringUtils.hasLength(project.getDescription()))
            newProject.setDescription(project.getDescription());
        if (project.getDeadline() != null)
            newProject.setDeadline(project.getDeadline());
        newProject.setUpdatedDate(Instant.now());
        iProjectRepository.save(newProject);
        return newProject;
    }

    public Project deleteProject(String id) {
        Project project = findProjectById(id);
        if (project == null) {
            return null;
        }
        iProjectRepository.deleteById(id);
        return project;
    }
}
