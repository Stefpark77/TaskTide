package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.service.TaskDependencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-dependency")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
@Slf4j
public class TaskDependencyController {

    private TaskDependencyService taskDependencyService;

    @Autowired
    public TaskDependencyController(TaskDependencyService taskDependencyService) {
        this.taskDependencyService = taskDependencyService;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByTaskId(@PathVariable String taskId) {
        log.info("getTaskDependenciesByTaskId: {}", taskId);
        return taskDependencyService.getTaskDependenciesByTaskId(taskId);
    }

    @GetMapping("/depends-on/{dependsOnId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByDependsOnId(@PathVariable String dependsOnId) {
        log.info("getTaskDependenciesByDependsOnId: {}", dependsOnId);
        return taskDependencyService.getTaskDependenciesByDependsOnId(dependsOnId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getAllTaskDependencies() {
        log.info("getAllTaskDependencies");
        return taskDependencyService.getAllTaskDependencies();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public TaskDependency addNewTaskDependency(@RequestBody TaskDependency dependency) {
        log.info("Add new task dependency: {}", dependency);
        return taskDependencyService.addNewTaskDependency(dependency);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public TaskDependency removeTaskDependency(@RequestParam String taskId, @RequestParam String dependsOnId) {
        log.info("Removing task dependency with taskId {} and dependsOnId {}", taskId, dependsOnId);
        return taskDependencyService.removeTaskDependency(taskId, dependsOnId);
    }
}
