package com.tasktide.taskServices.repository.interfaces;

import com.tasktide.taskServices.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface ITaskRepository extends CrudRepository<Task, String> {
    Iterable<Task> findTasksByUserIdContains(String userId);

    Iterable<Task> findTasksByProjectIdContains(String projectId);
}
