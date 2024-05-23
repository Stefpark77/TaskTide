package com.tasktide.projectServices.controller;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Project getProjectByProjectId(@PathVariable String projectId) {
        return projectService.getProjectByProjectId(projectId);
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Project addNewProject(@RequestBody Project project) {
        return projectService.addNewProject(project);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Project updateProject(@RequestBody Project project) {
        return projectService.updateProject(project);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Project removeProject(@RequestParam String projectId) {
        return projectService.removeProject(projectId);
    }
}
