package com.tasktide.taskServices.util;

import com.tasktide.taskServices.model.EstimatedDeadlineTask;
import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PrioritizationSorter {

    @Autowired
    private TaskDependencyRepository taskDependencyRepository;

    private static final Comparator<EstimatedDeadlineTask> COMPARISON_METHOD = Comparator.comparing(EstimatedDeadlineTask::getEstimatedDeadline)
            .thenComparing(EstimatedDeadlineTask::getPriority)
            .thenComparing(EstimatedDeadlineTask::getDifficulty)
            .thenComparing(EstimatedDeadlineTask::getName);

    public List<Task> sortByPrioritizationTasks(List<Task> tasks) {

        //get dependencies
        Map<String, List<String>> dependencyMap = getDependencyMap(tasks);

        //get estimated deadlines
        Map<EstimatedDeadlineTask, List<EstimatedDeadlineTask>> estimatedTasksMap = getTasksWithEstimatedDeadlines(tasks, dependencyMap);

        // Perform topological sort
        List<String> sortedTaskIds = topologicalSort(estimatedTasksMap);

        Map<String, Task> taskMap = tasks.stream()
                .collect(Collectors.toMap(Task::getId, task -> task));

        return sortedTaskIds.stream()
                .map(taskMap::get)
                .toList();
    }

    private List<String> topologicalSort(Map<EstimatedDeadlineTask, List<EstimatedDeadlineTask>> dependencyMap) {
        List<String> sortedTasks = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        // DFS
        List<EstimatedDeadlineTask> keys = new ArrayList<>(dependencyMap.keySet().stream().toList());
        keys.sort(COMPARISON_METHOD);
        for (EstimatedDeadlineTask task : keys) {
            if (!visited.contains(task.getTaskId())) {
                topologicalSortUtil(task, dependencyMap, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            sortedTasks.add(stack.pop());
        }

        return sortedTasks;
    }

    // Recursive utility function for topological sort
    private void topologicalSortUtil(EstimatedDeadlineTask task, Map<EstimatedDeadlineTask, List<EstimatedDeadlineTask>> dependencyMap, Set<String> visited, Stack<String> stack) {
        visited.add(task.getTaskId());

        List<EstimatedDeadlineTask> dependencies = dependencyMap.getOrDefault(task, new ArrayList<>());
        dependencies.stream().filter(Objects::nonNull).collect(Collectors.toList()).sort(COMPARISON_METHOD);
        for (EstimatedDeadlineTask dependency : dependencies) {
            if (dependency != null && !visited.contains(dependency.getTaskId())) {
                topologicalSortUtil(dependency, dependencyMap, visited, stack);
            }
        }

        stack.push(task.getTaskId());
    }

    private Map<String, List<String>> getDependencyMap(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.toMap(
                        Task::getId,
                        task -> taskDependencyRepository.findDependenciesByTaskId(task.getId()).stream().map(TaskDependency::getDependsOnId).toList()
                ));
    }

    private Map<EstimatedDeadlineTask, List<EstimatedDeadlineTask>> getTasksWithEstimatedDeadlines(List<Task> tasks, Map<String, List<String>> dependencyIdMap) {
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
        //get a dependency map with these estimated tasks
        return dependencyIdMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> estimatedTaskMap.get(entry.getKey()),
                        entry -> entry.getValue().stream()
                                .map(estimatedTaskMap::get)
                                .collect(Collectors.toList())));

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
