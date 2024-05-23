package com.tasktide.taskServices.repository;

import com.tasktide.taskServices.model.DependencyId;
import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.interfaces.ITaskDependencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Repository
public class TaskDependencyRepository {
    private final ITaskDependencyRepository iTaskDependencyRepository;

    @Autowired
    public TaskDependencyRepository(ITaskDependencyRepository iTaskRepository) {
        this.iTaskDependencyRepository = iTaskRepository;
    }

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
        if(dependency == null) {
            return null;
        }
        iTaskDependencyRepository.deleteById(new DependencyId(taskId, dependsOnId));
        return dependency;
    }

}
