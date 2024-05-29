package com.tasktide.userServices.controller;


import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/public")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class PublicUserController {
    private final UserService userService;
    @Autowired
    public PublicUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public User getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/projectId/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<User> getUserByProjectId(@PathVariable String projectId) {
        return userService.getUserByProjectId(projectId);
    }

}
