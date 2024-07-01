package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import com.tasktide.taskServices.repository.TaskRepository;
import com.tasktide.taskServices.util.PrioritizationSorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskDependencyService taskDependencyService;

    @Mock
    private PrioritizationSorter prioritizationSorter;

    @InjectMocks
    private TaskService taskService;

    private static final String TASK_ID = "test_task_id";
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";


    @Test
    public void getTaskByTaskId_success() {

        Task task = getTask();
        when(taskRepository.findTaskById(TASK_ID)).thenReturn(task);

        Task response = taskService.getTaskByTaskId(TASK_ID);

        assertNotNull(response);
        assertEquals(response, task);
        verify(taskRepository).findTaskById(TASK_ID);
    }

    @Test
    public void getTaskByTaskId_exception() {

        when(taskRepository.findTaskById(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getTaskByTaskId(TASK_ID));
        verify(taskRepository).findTaskById(TASK_ID);
    }


    @Test
    public void getTaskByUserId_success() {

        Task task = getTask();
        when(taskRepository.findTasksByUserId(USER_ID)).thenReturn(new ArrayList<>(List.of(task)));
        when(taskRepository.findTasksByProjectId(PROJECT_ID)).thenReturn(new ArrayList<>(List.of(task)));
        when(prioritizationSorter.sortByPrioritizationTasks(any())).thenReturn(new ArrayList<>(List.of(task)));

        List<Task> response = taskService.getTasksByUserId(USER_ID, new ArrayList<>(List.of(PROJECT_ID)));

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(taskRepository).findTasksByUserId(USER_ID);
    }

    @Test
    public void getTaskByUserId_exception() {

        when(taskRepository.findTasksByUserId(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getTasksByUserId(USER_ID, Collections.emptyList()));
        verify(taskRepository).findTasksByUserId(USER_ID);
    }


    @Test
    public void getTasksByProjectId_success() {

        Task task = getTask();
        when(taskRepository.findTasksByProjectId(PROJECT_ID)).thenReturn(List.of(task));

        List<Task> response = taskService.getTasksByProjectId(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(taskRepository).findTasksByProjectId(PROJECT_ID);
    }

    @Test
    public void getTasksByProjectId_exception() {

        when(taskRepository.findTasksByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getTasksByProjectId(PROJECT_ID));
        verify(taskRepository).findTasksByProjectId(PROJECT_ID);
    }


    @Test
    public void getDependedTasksByTaskId_success() {

        Task task = getTask();
        TaskDependency dependency = new TaskDependency();
        dependency.setDependsOnId(TASK_ID);
        when(taskDependencyService.getTaskDependenciesByTaskId(TASK_ID)).thenReturn(List.of(dependency));
        when(taskRepository.findTaskById(TASK_ID)).thenReturn(task);

        List<Task> response = taskService.getDependedTasksByTaskId(TASK_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(taskDependencyService).getTaskDependenciesByTaskId(TASK_ID);
    }

    @Test
    public void getDependedTasksByTaskId_exception() {

        when(taskDependencyService.getTaskDependenciesByTaskId(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getDependedTasksByTaskId(TASK_ID));
        verify(taskDependencyService).getTaskDependenciesByTaskId(TASK_ID);
    }


    @Test
    public void getDependingTasksByDependsOnId_success() {

        Task task = getTask();
        TaskDependency dependency = new TaskDependency();
        dependency.setTaskId(TASK_ID);
        when(taskDependencyService.getTaskDependenciesByDependsOnId(TASK_ID)).thenReturn(List.of(dependency));
        when(taskRepository.findTaskById(TASK_ID)).thenReturn(task);

        List<Task> response = taskService.getDependingTasksByDependsOnId(TASK_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(taskDependencyService).getTaskDependenciesByDependsOnId(TASK_ID);
    }

    @Test
    public void getDependingTasksByDependsOnId_exception() {

        when(taskDependencyService.getTaskDependenciesByDependsOnId(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getDependingTasksByDependsOnId(TASK_ID));
        verify(taskDependencyService).getTaskDependenciesByDependsOnId(TASK_ID);
    }

    @Test
    public void getAllTasks_success() {

        Task task = getTask();
        when(taskRepository.findAllTasks()).thenReturn(List.of(task));

        List<Task> response = taskService.getAllTasks();

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(taskRepository).findAllTasks();
    }

    @Test
    public void getAllTasks_exception() {

        when(taskRepository.findAllTasks()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.getAllTasks());
        verify(taskRepository).findAllTasks();
    }


    @Test
    public void addNewTask_success() {

        Task task = getTask();
        when(taskRepository.createTask(any())).thenReturn(task);

        Task response = taskService.addNewTask(task);

        assertNotNull(response);
        assertEquals(response, task);
        verify(taskRepository).createTask(any());
    }

    @Test
    public void addNewTask_exception() {

        when(taskRepository.createTask(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.addNewTask(getTask()));
        verify(taskRepository).createTask(any());
    }


    @Test
    public void updateTask_success() {

        Task task = getTask();
        when(taskRepository.updateTask(any())).thenReturn(task);

        Task response = taskService.updateTask(task);

        assertNotNull(response);
        assertEquals(response, task);
        verify(taskRepository).updateTask(any());
    }

    @Test
    public void updateTask_exception() {
        when(taskRepository.updateTask(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskService.updateTask(getTask()));
        verify(taskRepository).updateTask(any());
    }


    @Test
    public void deleteTask_success() {

        Task task = getTask();
        when(taskRepository.deleteTask(TASK_ID)).thenReturn(task);

        Task response = taskService.removeTask(TASK_ID);

        assertNotNull(response);
        assertEquals(response, task);
        verify(taskRepository).deleteTask(TASK_ID);
    }

    @Test
    public void deleteTask_exception() {
        doThrow(RuntimeException.class).when(taskRepository).deleteTask(TASK_ID);

        assertThrows(RuntimeException.class, () -> taskService.removeTask(TASK_ID));
        verify(taskRepository).deleteTask(TASK_ID);
    }


    private Task getTask() {
        Task task = new Task();
        task.setId(TASK_ID);
        return task;
    }


}
