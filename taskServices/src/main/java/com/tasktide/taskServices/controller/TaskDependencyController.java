package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.service.TaskDependencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-dependency")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class TaskDependencyController {

    private TaskDependencyService taskDependencyService;

    @Autowired
    public TaskDependencyController(TaskDependencyService taskDependencyService) {
        this.taskDependencyService = taskDependencyService;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByTaskId(@PathVariable String taskId) {
        return taskDependencyService.getTaskDependenciesByTaskId(taskId);
    }

    @GetMapping("/depends-on/{dependsOnId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByDependsOnId(@PathVariable String dependsOnId) {
        return taskDependencyService.getTaskDependenciesByDependsOnId(dependsOnId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getAllTaskDependencies() {
        return taskDependencyService.getAllTaskDependencies();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public TaskDependency addNewTaskDependency(@RequestBody TaskDependency dependency) {
        return taskDependencyService.addNewTaskDependency(dependency);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public TaskDependency removeTaskDependency(@RequestParam String taskId, @RequestParam String dependsOnId) {
        return taskDependencyService.removeTaskDependency(taskId, dependsOnId);
    }
}
