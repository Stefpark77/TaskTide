package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.service.TaskDependencyService;
import com.tasktide.taskServices.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Slf4j
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class TaskController {

    private final TaskService taskService;
    private final TaskDependencyService taskDependencyService;

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Task getTaskByTaskId(@PathVariable String taskId) {
        log.info("Getting task information for task id {}", taskId);
        return taskService.getTaskByTaskId(taskId);
    }

    @GetMapping("/{taskId}/depends-on/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependedTasksByTaskId(@PathVariable String taskId) {
        log.info("Getting depending task's information for task id {}", taskId);
        return taskService.getDependedTasksByTaskId(taskId);
    }

    @GetMapping("/{dependsOnId}/depended-tasks/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependingTasksByDependsOnId(@PathVariable String dependsOnId) {
        log.info("Getting depended task's information for task id {}", dependsOnId);
        return taskService.getDependingTasksByDependsOnId(dependsOnId);
    }


    @PostMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getTasksByUserId(@PathVariable String userId, @RequestBody(required = false) List<String> projectIds) {
        log.info("Getting tasks' information for user id {} with their project ids {}", userId, projectIds);
        return taskService.getTasksByUserId(userId, projectIds);
    }

    @GetMapping("/projectId/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getTasksByProjectId(@PathVariable String projectId) {
        log.info("Getting tasks' information for project id {}", projectId);
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getAllTasks() {
        log.info("Getting all tasks' information");
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Task addNewTask(@RequestBody Task task) {
        log.info("Adding a new task with name {}", task.getName());
        return taskService.addNewTask(task);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Task updateTask(@RequestBody Task task) {
        log.info("Updating existing task information for task id {}", task.getId());
        return taskService.updateTask(task);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Task removeTask(@RequestParam String taskId) {
        log.info("Removing existing task information for task id {}", taskId);
        taskDependencyService.removeAllDependenciesByTaskId(taskId);
        return taskService.removeTask(taskId);
    }


}
