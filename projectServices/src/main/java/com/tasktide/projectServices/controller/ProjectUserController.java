package com.tasktide.projectServices.controller;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.service.ProjectUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project-user")
@Slf4j
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<ProjectUser> getProjectUsersByUserId(@PathVariable String userId) {
        log.info("Getting all project ids for user id {}", userId);
        return projectUserService.getProjectUsersByUserId(userId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<ProjectUser> getAllProjectUsers() {
        log.info("Getting all project enrollment information");
        return projectUserService.getAllProjectUsers();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public ProjectUser addNewProjectUser(@RequestBody ProjectUser projectUser) {
        log.info("Enrolling new user with user id {} to project with project id {}", projectUser.getUserId(), projectUser.getProjectId());
        return projectUserService.addNewProjectUser(projectUser);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public ProjectUser removeProjectUser(@RequestParam String userId, @RequestParam String projectId) {
        log.info("Removing enrollment for user id {} from project id {}", userId, projectId);
        return projectUserService.removeProjectUser(userId, projectId);
    }
}
