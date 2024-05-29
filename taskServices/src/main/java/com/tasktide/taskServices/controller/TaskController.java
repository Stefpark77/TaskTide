package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
@Slf4j
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Task getTaskByTaskId(@PathVariable String taskId) {
        log.info("getTaskByTaskId: {}", taskId);
        return taskService.getTaskByTaskId(taskId);
    }

    @GetMapping("/{taskId}/depends-on/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependsOnTasksByTaskId(@PathVariable String taskId) {
        log.info("getDependsOnTasksByTaskId: {}", taskId);
        return taskService.getDependsOnTasksByTaskId(taskId);
    }

    @GetMapping("/{dependsOnId}/depended-tasks/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependedTasksByDependsOnId(@PathVariable String dependsOnId) {
        log.info("getDependedTasksByTaskId: {}", dependsOnId);
        return taskService.getDependedTasksByDependsOnId(dependsOnId);
    }


    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getTasksByUserId(@PathVariable String userId) {
        log.info("getTasksByUserId: {}", userId);
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/projectId/{projectId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getTasksByProjectId(@PathVariable String projectId) {
        log.info("getTasksByProjectId: {}", projectId);
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getAllTasks() {
        log.info("getAllTasks");
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Task addNewTask(@RequestBody Task task) {
        log.info("addNewTask: {}", task);
        return taskService.addNewTask(task);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Task updateTask(@RequestBody Task task) {
        log.info("updateTask: {}", task);
        return taskService.updateTask(task);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Task removeTask(@RequestParam String taskId) {
        log.info("removeTask: {}", taskId);
        return taskService.removeTask(taskId);
    }


}
