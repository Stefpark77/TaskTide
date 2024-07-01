package com.tasktide.taskServices.service;

import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskDependencyServiceTest {
    @Mock
    private TaskDependencyRepository taskDependencyRepository;

    @InjectMocks
    private TaskDependencyService taskDependencyService;

    private static final String TASK_ID = "test_task_id";
    private static final String DEPENDING = "test_depending_id";
    private static final String PROJECT_ID = "test_project_id";


    @Test
    public void getTaskDependenciesByTaskId_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(taskDependencyRepository.findDependenciesByTaskId(TASK_ID)).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyService.getTaskDependenciesByTaskId(TASK_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(taskDependencyRepository).findDependenciesByTaskId(TASK_ID);
    }

    @Test
    public void getTaskDependenciesByTaskId_exception() {

        when(taskDependencyRepository.findDependenciesByTaskId(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyService.getTaskDependenciesByTaskId(TASK_ID));
        verify(taskDependencyRepository).findDependenciesByTaskId(TASK_ID);
    }



    @Test
    public void getTaskDependenciesByDependsOnId_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(taskDependencyRepository.findDependenciesByDependsOnId(DEPENDING)).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyService.getTaskDependenciesByDependsOnId(DEPENDING);

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(taskDependencyRepository).findDependenciesByDependsOnId(DEPENDING);
    }

    @Test
    public void getTaskDependenciesByDependsOnId_exception() {

        when(taskDependencyRepository.findDependenciesByDependsOnId(DEPENDING)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyService.getTaskDependenciesByDependsOnId(DEPENDING));
        verify(taskDependencyRepository).findDependenciesByDependsOnId(DEPENDING);
    }


    @Test
    public void getAllTaskDependencies_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(taskDependencyRepository.findAllTaskDependencies()).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyService.getAllTaskDependencies();

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(taskDependencyRepository).findAllTaskDependencies();
    }

    @Test
    public void getAllTaskDependencies_exception() {

        when(taskDependencyRepository.findAllTaskDependencies()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyService.getAllTaskDependencies());
        verify(taskDependencyRepository).findAllTaskDependencies();
    }


    @Test
    public void addNewTaskDependency_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(taskDependencyRepository.createTaskDependency(any())).thenReturn(taskDependency);

        TaskDependency response = taskDependencyService.addNewTaskDependency(taskDependency);

        assertNotNull(response);
        assertEquals(response, taskDependency);
        verify(taskDependencyRepository).createTaskDependency(any());
    }

    @Test
    public void addNewTaskDependency_exception() {

        when(taskDependencyRepository.createTaskDependency(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyService.addNewTaskDependency(getTaskDependency()));
        verify(taskDependencyRepository).createTaskDependency(any());
    }


    @Test
    public void removeTaskDependency_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(taskDependencyRepository.deleteTaskDependency(TASK_ID, DEPENDING)).thenReturn(taskDependency);

        TaskDependency response = taskDependencyService.removeTaskDependency(TASK_ID, DEPENDING);

        assertNotNull(response);
        assertEquals(response, taskDependency);
        verify(taskDependencyRepository).deleteTaskDependency(TASK_ID, DEPENDING);
    }

    @Test
    public void removeTaskDependency_exception() {
        doThrow(RuntimeException.class).when(taskDependencyRepository).deleteTaskDependency(TASK_ID, DEPENDING);

        assertThrows(RuntimeException.class, () -> taskDependencyService.removeTaskDependency(TASK_ID, DEPENDING));
        verify(taskDependencyRepository).deleteTaskDependency(TASK_ID, DEPENDING);
    }

    @Test
    public void removeAllDependenciesByTaskId_success() {
        taskDependencyService.removeAllDependenciesByTaskId(TASK_ID);
        verify(taskDependencyRepository).deleteAllDependenciesByTaskId(TASK_ID);
    }

    @Test
    public void removeAllDependenciesByTaskId_exception() {
        doThrow(RuntimeException.class).when(taskDependencyRepository).deleteAllDependenciesByTaskId(TASK_ID);

        assertThrows(RuntimeException.class, () -> taskDependencyService.removeAllDependenciesByTaskId(TASK_ID));
        verify(taskDependencyRepository).deleteAllDependenciesByTaskId(TASK_ID);
    }


    private TaskDependency getTaskDependency() {
        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        return taskDependency;
    }


}
