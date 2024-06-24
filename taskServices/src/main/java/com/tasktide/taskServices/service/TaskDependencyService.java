package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskDependencyService {
    private final TaskDependencyRepository taskDependencyRepository;


    public List<TaskDependency> getTaskDependenciesByTaskId(String taskId) {
        return taskDependencyRepository.findDependenciesByTaskId(taskId);
    }

    public List<TaskDependency> getTaskDependenciesByDependsOnId(String dependsOnId) {
        return taskDependencyRepository.findDependenciesByDependsOnId(dependsOnId);
    }

    public List<TaskDependency> getAllTaskDependencies() {
        return taskDependencyRepository.findAllTaskDependencies();
    }

    public TaskDependency addNewTaskDependency(TaskDependency dependency) {
        return taskDependencyRepository.createTaskDependency(dependency);
    }

    public TaskDependency removeTaskDependency(String taskId, String dependsOnId) {
        return taskDependencyRepository.deleteTaskDependency(taskId, dependsOnId);
    }

    public void removeAllDependenciesByTaskId(String projectId) {
        taskDependencyRepository.deleteAllDependenciesByTaskId(projectId);
    }
}

