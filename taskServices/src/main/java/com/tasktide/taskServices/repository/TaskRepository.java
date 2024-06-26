package com.tasktide.taskServices.repository;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.repository.interfaces.ITaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final ITaskRepository iTaskRepository;

    public Task findTaskById(String id) {
        return iTaskRepository.findById(id).orElse(null);
    }

    public List<Task> findTasksByUserId(String userId) {
        return (List<Task>) iTaskRepository.findTasksByUserIdContains(userId);
    }

    public List<Task> findTasksByProjectId(String projectId) {
        return (List<Task>) iTaskRepository.findTasksByProjectIdContains(projectId);
    }

    public List<Task> findAllTasks() {
        return (List<Task>) iTaskRepository.findAll();
    }

    public Task createTask(Task task) {
        return iTaskRepository.save(task);
    }

    public Task updateTask(Task task) {
        Task newTask = findTaskById(task.getId());
        if (StringUtils.hasLength(task.getName()))
            newTask.setName(task.getName());
        if (StringUtils.hasLength(task.getDescription()))
            newTask.setDescription(task.getDescription());
        if (task.getProgress() != null)
            newTask.setProgress(task.getProgress());
        if (task.getDifficulty() != null)
            newTask.setDifficulty(task.getDifficulty());
        newTask.setDeadline(task.getDeadline());
        if (task.getPriority() != null)
            newTask.setPriority(task.getPriority());
        if (task.getStage() != null)
            newTask.setStage(task.getStage());
        newTask.setUserId(task.getUserId());
        newTask.setProjectId(task.getProjectId());
        newTask.setUpdatedDate(Instant.now());
        iTaskRepository.save(newTask);
        return newTask;
    }

    public Task deleteTask(String id) {
        Task task = findTaskById(id);
        if (task == null) {
            return null;
        }
        iTaskRepository.deleteById(id);
        return task;
    }

}
