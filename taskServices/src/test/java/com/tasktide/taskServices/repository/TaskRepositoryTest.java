package com.tasktide.taskServices.repository;

import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.repository.interfaces.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {
    @Mock
    private ITaskRepository iTaskRepository;

    @InjectMocks
    private TaskRepository taskRepository;

    private static final String TASK_ID = "test_task_id";
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";


    @Test
    public void findTaskById_success() {

        Task task = getTask();
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));

        Task response = taskRepository.findTaskById(TASK_ID);

        assertNotNull(response);
        assertEquals(response, task);
        verify(iTaskRepository).findById(TASK_ID);
    }

    @Test
    public void findTaskById_exception() {

        when(iTaskRepository.findById(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.findTaskById(TASK_ID));
        verify(iTaskRepository).findById(TASK_ID);
    }
    
    @Test
    public void findTaskByUserId_success() {

        Task task = getTask();
        when(iTaskRepository.findTasksByUserIdContains(USER_ID)).thenReturn(List.of(task));

        List<Task> response = taskRepository.findTasksByUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(iTaskRepository).findTasksByUserIdContains(USER_ID);
    }

    @Test
    public void findTaskByUserId_exception() {

        when(iTaskRepository.findTasksByUserIdContains(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.findTasksByUserId(USER_ID));
        verify(iTaskRepository).findTasksByUserIdContains(USER_ID);
    }


    @Test
    public void findTasksByProjectId_success() {

        Task task = getTask();
        when(iTaskRepository.findTasksByProjectIdContains(PROJECT_ID)).thenReturn(List.of(task));

        List<Task> response = taskRepository.findTasksByProjectId(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(iTaskRepository).findTasksByProjectIdContains(PROJECT_ID);
    }

    @Test
    public void findTasksByProjectId_exception() {

        when(iTaskRepository.findTasksByProjectIdContains(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.findTasksByProjectId(PROJECT_ID));
        verify(iTaskRepository).findTasksByProjectIdContains(PROJECT_ID);
    }


    @Test
    public void findAllTasks_success() {

        Task task = getTask();
        when(iTaskRepository.findAll()).thenReturn(List.of(task));

        List<Task> response = taskRepository.findAllTasks();

        assertNotNull(response);
        assertEquals(response.getFirst(), task);
        verify(iTaskRepository).findAll();
    }

    @Test
    public void findAllTasks_exception() {

        when(iTaskRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.findAllTasks());
        verify(iTaskRepository).findAll();
    }


    @Test
    public void createTask_success() {

        Task task = getTask();
        when(iTaskRepository.save(any())).thenReturn(task);

        Task response = taskRepository.createTask(task);

        assertNotNull(response);
        assertEquals(response, task);
        verify(iTaskRepository).save(any());
    }

    @Test
    public void createTask_exception() {

        when(iTaskRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.createTask(getTask()));
        verify(iTaskRepository).save(any());
    }


    @Test
    public void updateTask_success() {

        Task task = getTask();
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(iTaskRepository.save(any())).thenReturn(task);

        Task response = taskRepository.updateTask(task);

        assertNotNull(response);
        assertEquals(response, task);
        verify(iTaskRepository).findById(TASK_ID);
        verify(iTaskRepository).save(any());
    }

    @Test
    public void updateTask_exception() {
        Task task = getTask();
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(iTaskRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskRepository.updateTask(getTask()));
        verify(iTaskRepository).findById(TASK_ID);
        verify(iTaskRepository).save(any());
    }


    @Test
    public void deleteTask_success() {

        Task task = getTask();
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));

        Task response = taskRepository.deleteTask(TASK_ID);

        assertNotNull(response);
        assertEquals(response, task);
        verify(iTaskRepository).findById(TASK_ID);
        verify(iTaskRepository).deleteById(TASK_ID);
    }

    @Test
    public void deleteTask_success_nullTask() {
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

        Task response =  taskRepository.deleteTask(TASK_ID);

        assertNull(response);
        verify(iTaskRepository).findById(TASK_ID);
        verify(iTaskRepository, times(0)).deleteById(TASK_ID);
    }

    @Test
    public void deleteTask_exception() {
        Task task = getTask();
        when(iTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        doThrow(RuntimeException.class).when(iTaskRepository).deleteById(TASK_ID);

        assertThrows(RuntimeException.class, () -> taskRepository.deleteTask(TASK_ID));
        verify(iTaskRepository).findById(TASK_ID);
        verify(iTaskRepository).deleteById(TASK_ID);
    }


    private Task getTask() {
        Task task = new Task();
        task.setId(TASK_ID);
        return task;
    }
}
