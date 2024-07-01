package com.tasktide.taskServices.repository;

import com.tasktide.taskServices.model.DependencyId;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.repository.interfaces.ITaskDependencyRepository;
import com.tasktide.taskServices.repository.TaskDependencyRepository;
import com.tasktide.taskServices.repository.interfaces.ITaskDependencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskDependencyRepositoryTest {
    @Mock
    private ITaskDependencyRepository iTaskDependencyRepository;

    @InjectMocks
    private TaskDependencyRepository taskDependencyRepository;

    private static final String TASK_ID = "test_taskDependency_id";
    private static final String DEPENDING = "test_depending_id";


    @Test
    public void findDependenciesByTaskId_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyRepository.findDependenciesByTaskId(TASK_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
    }

    @Test
    public void findDependenciesByTaskId_exception() {

        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.findDependenciesByTaskId(TASK_ID));
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
    }


    @Test
    public void findDependenciesByDependsOnId_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.findDependenciesByDependsOnIdContains(DEPENDING)).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyRepository.findDependenciesByDependsOnId(DEPENDING);

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(iTaskDependencyRepository).findDependenciesByDependsOnIdContains(DEPENDING);
    }

    @Test
    public void findDependenciesByDependsOnId_exception() {

        when(iTaskDependencyRepository.findDependenciesByDependsOnIdContains(DEPENDING)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.findDependenciesByDependsOnId(DEPENDING));
        verify(iTaskDependencyRepository).findDependenciesByDependsOnIdContains(DEPENDING);
    }


    @Test
    public void findAllTaskDependencies_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.findAll()).thenReturn(List.of(taskDependency));

        List<TaskDependency> response = taskDependencyRepository.findAllTaskDependencies();

        assertNotNull(response);
        assertEquals(response.getFirst(), taskDependency);
        verify(iTaskDependencyRepository).findAll();
    }

    @Test
    public void findAllTaskDependencies_exception() {

        when(iTaskDependencyRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.findAllTaskDependencies());
        verify(iTaskDependencyRepository).findAll();
    }


    @Test
    public void createTaskDependency_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.save(any())).thenReturn(taskDependency);

        TaskDependency response = taskDependencyRepository.createTaskDependency(taskDependency);

        assertNotNull(response);
        assertEquals(response, taskDependency);
        verify(iTaskDependencyRepository).save(any());
    }

    @Test
    public void createTaskDependency_exception() {

        when(iTaskDependencyRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.createTaskDependency(getTaskDependency()));
        verify(iTaskDependencyRepository).save(any());
    }

    @Test
    public void deleteTaskDependency_success() {

        TaskDependency taskDependency = getTaskDependency();
        taskDependency.setTaskId(TASK_ID);
        taskDependency.setDependsOnId(DEPENDING);
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(List.of(taskDependency));

        TaskDependency response = taskDependencyRepository.deleteTaskDependency(TASK_ID, DEPENDING);

        assertNotNull(response);
        assertEquals(response, taskDependency);
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
        verify(iTaskDependencyRepository).deleteById(any(DependencyId.class));
    }

    @Test
    public void deleteTaskDependency_success_nullTaskDependency() {
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(Collections.emptyList());


        TaskDependency response =  taskDependencyRepository.deleteTaskDependency(TASK_ID, DEPENDING);

        assertNull(response);
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);;
        verify(iTaskDependencyRepository, times(0)).deleteById(any(DependencyId.class));
    }

    @Test
    public void deleteTaskDependency_exception() {
        TaskDependency taskDependency = getTaskDependency();
        taskDependency.setTaskId(TASK_ID);
        taskDependency.setDependsOnId(DEPENDING);
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(List.of(taskDependency));
        doThrow(RuntimeException.class).when(iTaskDependencyRepository).deleteById(any(DependencyId.class));

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.deleteTaskDependency(TASK_ID, DEPENDING));
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
        verify(iTaskDependencyRepository).deleteById(any(DependencyId.class));
    }

    @Test
    public void deleteAllDependenciesByTaskId_success() {

        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(List.of(taskDependency));
        when(iTaskDependencyRepository.findDependenciesByDependsOnIdContains(TASK_ID)).thenReturn(List.of(taskDependency));

        taskDependencyRepository.deleteAllDependenciesByTaskId(TASK_ID);

        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
        verify(iTaskDependencyRepository).findDependenciesByDependsOnIdContains(TASK_ID);
        verify(iTaskDependencyRepository, times(2)).deleteById(any(DependencyId.class));
    }


    @Test
    public void deleteAllDependenciesByTaskId_exception() {
        TaskDependency taskDependency = getTaskDependency();
        when(iTaskDependencyRepository.findTaskDependenciesByTaskIdContains(TASK_ID)).thenReturn(List.of(taskDependency));
        doThrow(RuntimeException.class).when(iTaskDependencyRepository).deleteById(any(DependencyId.class));

        assertThrows(RuntimeException.class, () -> taskDependencyRepository.deleteAllDependenciesByTaskId(TASK_ID));
        verify(iTaskDependencyRepository).findTaskDependenciesByTaskIdContains(TASK_ID);
        verify(iTaskDependencyRepository).deleteById(any(DependencyId.class));
    }

    private TaskDependency getTaskDependency() {
        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        return taskDependency;
    }
}
