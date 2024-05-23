package com.tasktide.projectServices.controller;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.service.ProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project-user")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class ProjectUserController {

    private ProjectUserService projectUserService;

    @Autowired
    public ProjectUserController(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<ProjectUser> getProjectUsersByUserId(@PathVariable String userId) {
        return projectUserService.getProjectUsersByUserId(userId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<ProjectUser> getAllProjectUsers() {
        return projectUserService.getAllProjectUsers();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public ProjectUser addNewProjectUser(@RequestBody ProjectUser projectUser) {
        return projectUserService.addNewProjectUser(projectUser);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public ProjectUser removeProjectUser(@RequestParam String userId, @RequestParam String projectId) {
        return projectUserService.removeProjectUser(userId, projectId);
    }
}
