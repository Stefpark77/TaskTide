package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.repository.ProjectRepository;
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
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private static final String PROJECT_ID = "test_project_id";
    private static final String USER_ID = "test_user_id";


    @Test
    public void getProjectByProjectId_success() {

        Project project = getProject();
        when(projectRepository.findProjectById(PROJECT_ID)).thenReturn(project);

        Project response = projectService.getProjectByProjectId(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, project);
        verify(projectRepository).findProjectById(PROJECT_ID);
    }

    @Test
    public void getProjectByProjectId_exception() {

        when(projectRepository.findProjectById(PROJECT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectService.getProjectByProjectId(PROJECT_ID));
        verify(projectRepository).findProjectById(PROJECT_ID);
    }





    @Test
    public void getAllProjects_success() {

        Project project = getProject();
        when(projectRepository.findAllProjects()).thenReturn(List.of(project));

        List<Project> response = projectService.getAllProjects();

        assertNotNull(response);
        assertEquals(response.getFirst(), project);
        verify(projectRepository).findAllProjects();
    }

    @Test
    public void getAllProjects_exception() {

        when(projectRepository.findAllProjects()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectService.getAllProjects());
        verify(projectRepository).findAllProjects();
    }


    @Test
    public void addNewProject_success() {

        Project project = getProject();
        when(projectRepository.createProject(any())).thenReturn(project);

        Project response = projectService.addNewProject(project);

        assertNotNull(response);
        assertEquals(response, project);
        verify(projectRepository).createProject(any());
    }

    @Test
    public void addNewProject_exception() {

        when(projectRepository.createProject(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectService.addNewProject(getProject()));
        verify(projectRepository).createProject(any());
    }


    @Test
    public void updateProject_success() {

        Project project = getProject();
        when(projectRepository.updateProject(any())).thenReturn(project);

        Project response = projectService.updateProject(project);

        assertNotNull(response);
        assertEquals(response, project);
        verify(projectRepository).updateProject(any());
    }

    @Test
    public void updateProject_exception() {
        when(projectRepository.updateProject(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectService.updateProject(getProject()));
        verify(projectRepository).updateProject(any());
    }


    @Test
    public void deleteProject_success() {

        Project project = getProject();
        when(projectRepository.deleteProject(PROJECT_ID)).thenReturn(project);

        Project response = projectService.removeProject(PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, project);
        verify(projectRepository).deleteProject(PROJECT_ID);
    }

    @Test
    public void deleteProject_exception() {
        doThrow(RuntimeException.class).when(projectRepository).deleteProject(PROJECT_ID);

        assertThrows(RuntimeException.class, () -> projectService.removeProject(PROJECT_ID));
        verify(projectRepository).deleteProject(PROJECT_ID);
    }


    private Project getProject() {
        Project project = new Project();
        project.setId(PROJECT_ID);
        return project;
    }


}
