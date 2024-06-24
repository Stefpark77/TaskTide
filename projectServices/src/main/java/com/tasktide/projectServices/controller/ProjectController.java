package com.tasktide.projectServices.controller;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.service.ProjectService;
import com.tasktide.projectServices.service.ProjectUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectUserService projectUserService;

    @GetMapping("/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Project getProjectByProjectId(@PathVariable String projectId) {
        log.info("Getting project information for project id {}", projectId);
        return projectService.getProjectByProjectId(projectId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Project> getAllProjects() {
        log.info("Getting all projects' information");
        return projectService.getAllProjects();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Project addNewProject(@RequestBody Project project) {
        log.info("Adding a new project with name {}", project.getName());
        return projectService.addNewProject(project);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Project updateProject(@RequestBody Project project) {
        log.info("Updating existing project information for project id {}", project.getId());
        return projectService.updateProject(project);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Project removeProject(@RequestParam String projectId) {
        log.info("Removing existing project information for project id {}", projectId);
        projectUserService.removeAllProjectUsersForProjectId(projectId);
        return projectService.removeProject(projectId);
    }
}
