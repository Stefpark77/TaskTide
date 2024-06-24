package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.service.TaskDependencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-dependency")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
@Slf4j
public class TaskDependencyController {

    private final TaskDependencyService taskDependencyService;

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByTaskId(@PathVariable String taskId) {
        log.info("Getting all depending task ids for task id {}", taskId);
        return taskDependencyService.getTaskDependenciesByTaskId(taskId);
    }

    @GetMapping("/depends-on/{dependsOnId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getTaskDependenciesByDependsOnId(@PathVariable String dependsOnId) {
        log.info("Getting all depended task ids for task id {}", dependsOnId);
        return taskDependencyService.getTaskDependenciesByDependsOnId(dependsOnId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<TaskDependency> getAllTaskDependencies() {
        log.info("Getting all task dependencies information");
        return taskDependencyService.getAllTaskDependencies();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public TaskDependency addNewTaskDependency(@RequestBody TaskDependency dependency) {
        log.info("Adding a new dependency: task id {} depends on task id {}", dependency.getTaskId(), dependency.getDependsOnId());
        return taskDependencyService.addNewTaskDependency(dependency);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public TaskDependency removeTaskDependency(@RequestParam String taskId, @RequestParam String dependsOnId) {
        log.info("Removing dependency between task id {} and task id {}", taskId, dependsOnId);
        return taskDependencyService.removeTaskDependency(taskId, dependsOnId);
    }
}
