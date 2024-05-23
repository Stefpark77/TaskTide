package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.repository.TaskRepository;
import com.tasktide.taskServices.util.PrioritizationSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private PrioritizationSorter prioritizationSorter;
    private TaskDependencyService taskDependencyService;

    @Autowired
    public TaskService(TaskRepository taskRepository, PrioritizationSorter prioritizationSorter, TaskDependencyService taskDependencyService) {
        this.taskRepository = taskRepository;
        this.prioritizationSorter = prioritizationSorter;
        this.taskDependencyService = taskDependencyService;
    }

    public Task getTaskByTaskId(String taskId) {
        return taskRepository.findTaskById(taskId);
    }

    public List<Task> getTasksByUserId(String userId) {
        return prioritizationSorter.sortByPrioritizationTasks(taskRepository.findTasksByUserId(userId));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllTasks();
    }
    public Task addNewTask(Task task) {
        return taskRepository.createTask(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public Task removeTask(String taskId) {
        return taskRepository.deleteTask(taskId);
    }

    public List<Task> getDependsOnTasksByTaskId(String taskId) {
        return taskDependencyService.getTaskDependenciesByTaskId(taskId).stream()
                .map(dependency -> getTaskByTaskId(dependency.getDependsOnId()))
                .toList();

    }

    public List<Task> getDependedTasksByDependsOnId(String dependsOnId) {
        return taskDependencyService.getTaskDependenciesByDependsOnId(dependsOnId).stream()
                .map(dependency -> getTaskByTaskId(dependency.getTaskId()))
                .toList();
    }

}
