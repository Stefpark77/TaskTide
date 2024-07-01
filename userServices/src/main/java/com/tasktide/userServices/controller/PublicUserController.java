package com.tasktide.userServices.controller;


import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/public")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class PublicUserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public User getUserByUserId(@PathVariable String userId) {
        log.info("Getting user information for user id {}", userId);
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public User getUserByUsername(@PathVariable String username) {
        log.info("Getting user information by username");
        return userService.getUserByUsername(username);
    }

    @GetMapping("/projectId/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<User> getUsersByProjectId(@PathVariable String projectId) {
        log.info("Getting user information by project id {}", projectId);
        return userService.getUsersByProjectId(projectId);
    }

}
