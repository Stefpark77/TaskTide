package com.tasktide.projectServices.service;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.repository.ProjectUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectUserServiceTest {
    @Mock
    private ProjectUserRepository projectUserRepository;

    @InjectMocks
    private ProjectUserService projectUserService;

    private static final String PROJECT_ID = "test_projectUser_id";
    private static final String USER_ID = "test_user_id";


    @Test
    public void getProjectUsersByUserId_success() {

        ProjectUser projectUser = getProjectUser();
        when(projectUserRepository.findProjectUsersForUserId(USER_ID)).thenReturn(List.of(projectUser));

        List<ProjectUser> response = projectUserService.getProjectUsersByUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), projectUser);
        verify(projectUserRepository).findProjectUsersForUserId(USER_ID);
    }

    @Test
    public void getProjectUsersByUserId_exception() {

        when(projectUserRepository.findProjectUsersForUserId(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserService.getProjectUsersByUserId(USER_ID));
        verify(projectUserRepository).findProjectUsersForUserId(USER_ID);
    }

    @Test
    public void getAllProjectUsers_success() {

        ProjectUser projectUser = getProjectUser();
        when(projectUserRepository.findAllProjectUsers()).thenReturn(List.of(projectUser));

        List<ProjectUser> response = projectUserService.getAllProjectUsers();

        assertNotNull(response);
        assertEquals(response.getFirst(), projectUser);
        verify(projectUserRepository).findAllProjectUsers();
    }

    @Test
    public void getAllProjectUsers_exception() {

        when(projectUserRepository.findAllProjectUsers()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserService.getAllProjectUsers());
        verify(projectUserRepository).findAllProjectUsers();
    }


    @Test
    public void addNewProjectUser_success() {

        ProjectUser projectUser = getProjectUser();
        when(projectUserRepository.createProjectUser(any())).thenReturn(projectUser);

        ProjectUser response = projectUserService.addNewProjectUser(projectUser);

        assertNotNull(response);
        assertEquals(response, projectUser);
        verify(projectUserRepository).createProjectUser(any());
    }

    @Test
    public void addNewProjectUser_exception() {

        when(projectUserRepository.createProjectUser(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserService.addNewProjectUser(getProjectUser()));
        verify(projectUserRepository).createProjectUser(any());
    }

    @Test
    public void removeProjectUser_success() {

        ProjectUser projectUser = getProjectUser();
        when(projectUserRepository.deleteProjectUser(USER_ID, PROJECT_ID)).thenReturn(projectUser);

        ProjectUser response = projectUserService.removeProjectUser(USER_ID, PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, projectUser);
        verify(projectUserRepository).deleteProjectUser(USER_ID, PROJECT_ID);
    }

    @Test
    public void removeProjectUser_exception() {
        doThrow(RuntimeException.class).when(projectUserRepository).deleteProjectUser(USER_ID, PROJECT_ID);

        assertThrows(RuntimeException.class, () -> projectUserService.removeProjectUser(USER_ID, PROJECT_ID));
        verify(projectUserRepository).deleteProjectUser(USER_ID, PROJECT_ID);
    }


    @Test
    public void removeAllProjectUsersForProjectId_success() {

        projectUserService.removeAllProjectUsersForProjectId(PROJECT_ID);

        verify(projectUserRepository).deleteAllProjectUsersForProjectId(PROJECT_ID);
    }

    @Test
    public void removeAllProjectUsersForProjectId_exception() {
        doThrow(RuntimeException.class).when(projectUserRepository).deleteAllProjectUsersForProjectId(PROJECT_ID);

        assertThrows(RuntimeException.class, () -> projectUserService.removeAllProjectUsersForProjectId(PROJECT_ID));
        verify(projectUserRepository).deleteAllProjectUsersForProjectId(PROJECT_ID);
    }

    private ProjectUser getProjectUser() {
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(PROJECT_ID);
        projectUser.setUserId(USER_ID);
        return projectUser;
    }


}
