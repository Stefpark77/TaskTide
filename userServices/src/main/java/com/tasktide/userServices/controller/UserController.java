package com.tasktide.userServices.controller;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('adminRead')")
    public User getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('adminRead')")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('adminRead')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('adminCreate')")
    public User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('adminUpdate')")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('adminDelete')")
    public User removeUser(@RequestParam String userId) {
        return userService.removeUser(userId);
    }
}
