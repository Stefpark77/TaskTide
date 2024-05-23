package com.tasktide.taskServices.repository.interfaces;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import org.springframework.data.repository.CrudRepository;

public interface ITaskRepository extends CrudRepository<Task, String> {
    Iterable<Task> findTasksByUserIdContains(String userId);
}
