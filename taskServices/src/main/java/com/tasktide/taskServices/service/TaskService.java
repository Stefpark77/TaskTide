package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.repository.TaskRepository;
import com.tasktide.taskServices.util.PrioritizationSorter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final PrioritizationSorter prioritizationSorter;
    private final TaskDependencyService taskDependencyService;

    public Task getTaskByTaskId(String taskId) {
        return taskRepository.findTaskById(taskId);
    }

    public List<Task> getTasksByUserId(String userId, List<String> projectIds) {
        List<Task> assignedTasks = taskRepository.findTasksByUserId(userId);
        List<Task> projectTasks = new ArrayList<>();
        if (projectIds != null) {
            projectTasks = projectIds.stream().flatMap(projectId -> getTasksByProjectId(projectId).stream()).toList();
        }
        assignedTasks.addAll(projectTasks);
        List<Task> userTasks = assignedTasks.stream().distinct().toList();
        return prioritizationSorter.sortByPrioritizationTasks(userTasks);
    }

    public List<Task> getTasksByProjectId(String projectId) {
        return taskRepository.findTasksByProjectId(projectId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllTasks();
    }

    public Task addNewTask(Task task) {
        return taskRepository.createTask(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public Task removeTask(String taskId) {
        return taskRepository.deleteTask(taskId);
    }

    public List<Task> getDependedTasksByTaskId(String taskId) {
        return taskDependencyService.getTaskDependenciesByTaskId(taskId).stream()
                .map(dependency -> getTaskByTaskId(dependency.getDependsOnId()))
                .toList();

    }

    public List<Task> getDependingTasksByDependsOnId(String dependsOnId) {
        return taskDependencyService.getTaskDependenciesByDependsOnId(dependsOnId).stream()
                .map(dependency -> getTaskByTaskId(dependency.getTaskId()))
                .toList();
    }

}
