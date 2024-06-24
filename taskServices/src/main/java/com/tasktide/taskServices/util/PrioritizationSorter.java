package com.tasktide.taskServices.util;

import com.tasktide.taskServices.model.EstimatedDeadlineTask;
import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PrioritizationSorter {
    private final TaskDependencyRepository taskDependencyRepository;

    private static final Comparator<EstimatedDeadlineTask> COMPARISON_METHOD = Comparator.comparing(EstimatedDeadlineTask::getEstimatedDeadline)
            .thenComparing(EstimatedDeadlineTask::getPriority)
            .thenComparing(EstimatedDeadlineTask::getDifficulty, Comparator.reverseOrder())
            .thenComparing(EstimatedDeadlineTask::getName);

    public List<Task> sortByPrioritizationTasks(List<Task> tasks) {

        //get dependencies
        Map<String, List<String>> dependencyMap = getDependencyMap(tasks);

        //get estimated deadlines
        List<EstimatedDeadlineTask> estimatedTasks = getTasksWithEstimatedDeadlines(tasks, dependencyMap);

        //sort tasks
        estimatedTasks.sort(COMPARISON_METHOD);

        //changes tasks with estimated deadlines to their original model
        Map<String, Task> taskMap = tasks.stream()
                .collect(Collectors.toMap(Task::getId, task -> task));

        return estimatedTasks.stream()
                .map(eTask -> taskMap.get(eTask.getTaskId()))
                .toList();
    }

    private Map<String, List<String>> getDependencyMap(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.toMap(
                        Task::getId,
                        task -> taskDependencyRepository.findDependenciesByDependsOnId(task.getId()).stream().map(TaskDependency::getTaskId).toList()
                ));
    }

    private List<EstimatedDeadlineTask> getTasksWithEstimatedDeadlines(List<Task> tasks, Map<String, List<String>> dependencyIdMap) {
        //prepare a lists of tasks with no estimated deadline
        List<EstimatedDeadlineTask> estimatedTasks = tasks.stream()
                .map(this::transformTask)
                .toList();
        //prepare a map with the id and the task
        Map<String, EstimatedDeadlineTask> estimatedTaskMap = estimatedTasks.stream()
                .collect(Collectors.toMap(EstimatedDeadlineTask::getTaskId, task -> task));
        //update the estimated deadline for each task
        for (EstimatedDeadlineTask estimatedDeadlineTask : estimatedTasks) {
            updateEstimatedDeadlineForTask(estimatedDeadlineTask, dependencyIdMap, estimatedTaskMap);
        }

        return new ArrayList<>(estimatedTasks);

    }

    private void updateEstimatedDeadlineForTask(EstimatedDeadlineTask currentTask,
                                                Map<String, List<String>> dependencyIdMap,
                                                Map<String, EstimatedDeadlineTask> estimatedTaskMap) {
        //check if we need to update the estimated deadline
        if (currentTask.getEstimatedDeadline() != null) {
            return;
        }
        //start with the default deadline
        Instant currentEstimatedDeadline = currentTask.getDeadline();

        //check dependencies
        List<String> dependencyIds = dependencyIdMap.get(currentTask.getTaskId());

        for (String dependencyId : dependencyIds) {
            //get task from map
            EstimatedDeadlineTask dependency = estimatedTaskMap.get(dependencyId);
            if (dependency != null) {
                //make sure their estimated deadline exists
                updateEstimatedDeadlineForTask(dependency, dependencyIdMap, estimatedTaskMap);
                //update currentEstimatedDeadline
                Instant estimatedTime = dependency.getEstimatedDeadline().minus(dependency.getDifficulty(), ChronoUnit.DAYS);
                if (currentEstimatedDeadline == null || currentEstimatedDeadline.isAfter(estimatedTime)) {
                    currentEstimatedDeadline = estimatedTime;
                }
            }
        }
        if (currentEstimatedDeadline == null) {
            currentEstimatedDeadline = Instant.MAX;
        }
        currentTask.setEstimatedDeadline(currentEstimatedDeadline);
    }

    private EstimatedDeadlineTask transformTask(Task task) {
        EstimatedDeadlineTask estimatedDeadlineTask = new EstimatedDeadlineTask();
        estimatedDeadlineTask.setTaskId(task.getId());
        estimatedDeadlineTask.setName(task.getName());
        estimatedDeadlineTask.setDeadline(task.getDeadline());
        estimatedDeadlineTask.setDifficulty(task.getDifficulty());
        estimatedDeadlineTask.setPriority(task.getPriority());
        return estimatedDeadlineTask;
    }

}
