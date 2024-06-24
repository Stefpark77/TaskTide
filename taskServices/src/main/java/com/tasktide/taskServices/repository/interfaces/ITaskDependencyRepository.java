package com.tasktide.taskServices.repository.interfaces;

import com.tasktide.taskServices.model.DependencyId;
import com.tasktide.taskServices.model.TaskDependency;
import org.springframework.data.repository.CrudRepository;

public interface ITaskDependencyRepository extends CrudRepository<TaskDependency, DependencyId> {
    Iterable<TaskDependency> findTaskDependenciesByTaskIdContains(String taskId);

    Iterable<TaskDependency> findDependenciesByDependsOnIdContains(String dependsOnId);
}
