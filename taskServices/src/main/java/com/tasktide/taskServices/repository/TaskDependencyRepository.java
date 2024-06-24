package com.tasktide.taskServices.repository;

import com.tasktide.taskServices.model.DependencyId;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.interfaces.ITaskDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class TaskDependencyRepository {
    private final ITaskDependencyRepository iTaskDependencyRepository;


    public List<TaskDependency> findDependenciesByTaskId(String taskId) {
        return (List<TaskDependency>) iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(taskId);
    }


    public List<TaskDependency> findDependenciesByDependsOnId(String dependsOnId) {
        return (List<TaskDependency>) iTaskDependencyRepository.findDependenciesByDependsOnIdContains(dependsOnId);
    }

    public List<TaskDependency> findAllTaskDependencies() {
        return (List<TaskDependency>) iTaskDependencyRepository.findAll();
    }

    public TaskDependency createTaskDependency(TaskDependency dependency) {
        return iTaskDependencyRepository.save(dependency);
    }

    public TaskDependency deleteTaskDependency(String taskId, String dependsOnId) {
        TaskDependency dependency = findDependenciesByTaskId(taskId).stream()
                .filter(x -> Objects.equals(x.getDependsOnId(), dependsOnId))
                .findFirst().orElse(null);
        if (dependency == null) {
            return null;
        }
        iTaskDependencyRepository.deleteById(new DependencyId(taskId, dependsOnId));
        return dependency;
    }

    public void deleteAllDependenciesByTaskId(String taskId) {
        List<TaskDependency> dependenciesToDelete = findDependenciesByTaskId(taskId);
        for (TaskDependency taskDependency : dependenciesToDelete) {
            iTaskDependencyRepository.deleteById(new DependencyId(taskDependency.getTaskId(), taskDependency.getDependsOnId()));
        }

        List<TaskDependency> dependenciesByToDelete = findDependenciesByDependsOnId(taskId);
        for (TaskDependency taskDependency : dependenciesByToDelete) {
            iTaskDependencyRepository.deleteById(new DependencyId(taskDependency.getTaskId(), taskDependency.getDependsOnId()));
        }
    }

}
