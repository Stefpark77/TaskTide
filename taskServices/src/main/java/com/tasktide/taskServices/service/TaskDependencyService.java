package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import com.tasktide.taskServices.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDependencyService {
    private TaskDependencyRepository taskDependencyRepository;

    @Autowired
    public TaskDependencyService(TaskDependencyRepository taskDependencyRepository) {
        this.taskDependencyRepository = taskDependencyRepository;
    }

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
}

