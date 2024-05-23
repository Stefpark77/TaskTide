package com.tasktide.taskServices.controller;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Task getTaskByTaskId(@PathVariable String taskId) {
        return taskService.getTaskByTaskId(taskId);
    }

    @GetMapping("/{taskId}/depends-on/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependsOnTasksByTaskId(@PathVariable String taskId) {
        return taskService.getDependsOnTasksByTaskId(taskId);
    }

    @GetMapping("/{dependsOnId}/depended-tasks/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getDependedTasksByDependsOnId(@PathVariable String dependsOnId) {
        return taskService.getDependedTasksByDependsOnId(dependsOnId);
    }


    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getTasksByUserId(@PathVariable String userId) {
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Task addNewTask(@RequestBody Task task) {
        return taskService.addNewTask(task);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Task removeTask(@RequestParam String taskId) {
        return taskService.removeTask(taskId);
    }


}
