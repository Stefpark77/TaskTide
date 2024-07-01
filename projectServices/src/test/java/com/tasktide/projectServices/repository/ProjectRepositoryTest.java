package com.tasktide.projectServices.repository;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.repository.interfaces.IProjectRepository;
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
public class ProjectRepositoryTest {
    @Mock
    private IProjectRepository iProjectRepository;

    @InjectMocks
    private ProjectRepository projectRepository;

    private static final String PROJECT_ID = "test_project_id";
    private static final String USER_ID = "test_user_id";


    @Test
    public void findProjectById_success() {

        Project project = getProject();
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));

        Project response = projectRepository.findProjectById(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, project);
        verify(iProjectRepository).findById(PROJECT_ID);
    }

    @Test
    public void findProjectById_exception() {

        when(iProjectRepository.findById(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectRepository.findProjectById(PROJECT_ID));
        verify(iProjectRepository).findById(PROJECT_ID);
    }



    @Test
    public void findAllProjects_success() {

        Project project = getProject();
        when(iProjectRepository.findAll()).thenReturn(List.of(project));

        List<Project> response = projectRepository.findAllProjects();

        assertNotNull(response);
        assertEquals(response.getFirst(), project);
        verify(iProjectRepository).findAll();
    }

    @Test
    public void findAllProjects_exception() {

        when(iProjectRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectRepository.findAllProjects());
        verify(iProjectRepository).findAll();
    }


    @Test
    public void createProject_success() {

        Project project = getProject();
        when(iProjectRepository.save(any())).thenReturn(project);

        Project response = projectRepository.createProject(project);

        assertNotNull(response);
        assertEquals(response, project);
        verify(iProjectRepository).save(any());
    }

    @Test
    public void createProject_exception() {

        when(iProjectRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectRepository.createProject(getProject()));
        verify(iProjectRepository).save(any());
    }


    @Test
    public void updateProject_success() {

        Project project = getProject();
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        when(iProjectRepository.save(any())).thenReturn(project);

        Project response = projectRepository.updateProject(project);

        assertNotNull(response);
        assertEquals(response, project);
        verify(iProjectRepository).findById(PROJECT_ID);
        verify(iProjectRepository).save(any());
    }

    @Test
    public void updateProject_exception() {
        Project project = getProject();
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        when(iProjectRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectRepository.updateProject(getProject()));
        verify(iProjectRepository).findById(PROJECT_ID);
        verify(iProjectRepository).save(any());
    }


    @Test
    public void deleteProject_success() {

        Project project = getProject();
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));

        Project response = projectRepository.deleteProject(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, project);
        verify(iProjectRepository).findById(PROJECT_ID);
        verify(iProjectRepository).deleteById(PROJECT_ID);
    }

    @Test
    public void deleteProject_success_nullProject() {
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Project response =  projectRepository.deleteProject(PROJECT_ID);

        assertNull(response);
        verify(iProjectRepository).findById(PROJECT_ID);
        verify(iProjectRepository, times(0)).deleteById(PROJECT_ID);
    }

    @Test
    public void deleteProject_exception() {
        Project project = getProject();
        when(iProjectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        doThrow(RuntimeException.class).when(iProjectRepository).deleteById(PROJECT_ID);

        assertThrows(RuntimeException.class, () -> projectRepository.deleteProject(PROJECT_ID));
        verify(iProjectRepository).findById(PROJECT_ID);
        verify(iProjectRepository).deleteById(PROJECT_ID);
    }


    private Project getProject() {
        Project project = new Project();
        project.setId(PROJECT_ID);
        return project;
    }
}
