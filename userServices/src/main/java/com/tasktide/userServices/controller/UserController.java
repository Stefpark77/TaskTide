package com.tasktide.userServices.controller;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('adminRead')")
    public User getUserByUserId(@PathVariable String userId) {
        log.info("Getting user information for user id {}", userId);
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('adminRead')")
    public User getUserByUsername(@PathVariable String username) {
        log.info("Getting user information by username");
        return userService.getUserByUsername(username);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('adminRead')")
    public List<User> getAllUsers() {
        log.info("Getting all users' information");
        return userService.getAllUsers();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('adminCreate')")
    public User addNewUser(@RequestBody User user) {
        log.info("Adding a new user");
        return userService.addNewUser(user);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('adminUpdate')")
    public User updateUser(@RequestBody User user) {
        log.info("Updating existing user information for user id {}", user.getId());
        return userService.updateUser(user);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('adminDelete')")
    public User removeUser(@RequestParam String userId) {
        log.info("Removing existing user information for user id {}", userId);
        return userService.removeUser(userId);
    }
}
