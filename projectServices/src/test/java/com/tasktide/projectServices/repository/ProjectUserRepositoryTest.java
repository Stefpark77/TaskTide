package com.tasktide.projectServices.repository;

import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.model.ProjectUserId;
import com.tasktide.projectServices.repository.interfaces.IProjectUserRepository;
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
public class ProjectUserRepositoryTest {
    @Mock
    private IProjectUserRepository iProjectUserRepository;

    @InjectMocks
    private ProjectUserRepository projectUserRepository;

    private static final String PROJECT_ID = "test_projectUser_id";
    private static final String USER_ID = "test_user_id";


    @Test
    public void findProjectUsersForUserId_success() {

        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findProjectUserByUserIdContains(USER_ID)).thenReturn(List.of(projectUser));

        List<ProjectUser> response = projectUserRepository.findProjectUsersForUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), projectUser);
        verify(iProjectUserRepository).findProjectUserByUserIdContains(USER_ID);
    }

    @Test
    public void findProjectUsersForUserId_exception() {

        when(iProjectUserRepository.findProjectUserByUserIdContains(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserRepository.findProjectUsersForUserId(USER_ID));
        verify(iProjectUserRepository).findProjectUserByUserIdContains(USER_ID);
    }

    @Test
    public void findAllProjectUsers_success() {

        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findAll()).thenReturn(List.of(projectUser));

        List<ProjectUser> response = projectUserRepository.findAllProjectUsers();

        assertNotNull(response);
        assertEquals(response.getFirst(), projectUser);
        verify(iProjectUserRepository).findAll();
    }

    @Test
    public void findAllProjectUsers_exception() {

        when(iProjectUserRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserRepository.findAllProjectUsers());
        verify(iProjectUserRepository).findAll();
    }


    @Test
    public void createProjectUser_success() {

        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.save(any())).thenReturn(projectUser);

        ProjectUser response = projectUserRepository.createProjectUser(projectUser);

        assertNotNull(response);
        assertEquals(response, projectUser);
        verify(iProjectUserRepository).save(any());
    }

    @Test
    public void createProjectUser_exception() {

        when(iProjectUserRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> projectUserRepository.createProjectUser(getProjectUser()));
        verify(iProjectUserRepository).save(any());
    }

    @Test
    public void deleteProjectUser_success() {

        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findProjectUserByUserIdContains(USER_ID)).thenReturn(List.of(projectUser));

        ProjectUser response = projectUserRepository.deleteProjectUser(USER_ID, PROJECT_ID);

        assertNotNull(response);
        assertEquals(response, projectUser);
        verify(iProjectUserRepository).findProjectUserByUserIdContains(USER_ID);
        verify(iProjectUserRepository).deleteById(any(ProjectUserId.class));
    }

    @Test
    public void deleteProjectUser_success_nullProjectUser() {
        when(iProjectUserRepository.findProjectUserByUserIdContains(USER_ID)).thenReturn(Collections.emptyList());

        ProjectUser response =  projectUserRepository.deleteProjectUser(USER_ID, PROJECT_ID);

        assertNull(response);
        verify(iProjectUserRepository).findProjectUserByUserIdContains(USER_ID);
        verify(iProjectUserRepository, times(0)).deleteById(any(ProjectUserId.class));
    }

    @Test
    public void deleteProjectUser_exception() {
        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findProjectUserByUserIdContains(USER_ID)).thenReturn(List.of(projectUser));
        doThrow(RuntimeException.class).when(iProjectUserRepository).deleteById(any(ProjectUserId.class));

        assertThrows(RuntimeException.class, () -> projectUserRepository.deleteProjectUser(USER_ID, PROJECT_ID));
        verify(iProjectUserRepository).findProjectUserByUserIdContains(USER_ID);
        verify(iProjectUserRepository).deleteById(any(ProjectUserId.class));
    }


    @Test
    public void deleteAllProjectUsersForProjectId_success() {

        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findProjectUserByProjectIdContains(PROJECT_ID)).thenReturn(List.of(projectUser));

        projectUserRepository.deleteAllProjectUsersForProjectId(PROJECT_ID);

        verify(iProjectUserRepository).findProjectUserByProjectIdContains(PROJECT_ID);
        verify(iProjectUserRepository).deleteById(any(ProjectUserId.class));
    }

    @Test
    public void deleteAllProjectUsersForProjectId_exception() {
        ProjectUser projectUser = getProjectUser();
        when(iProjectUserRepository.findProjectUserByProjectIdContains(PROJECT_ID)).thenReturn(List.of(projectUser));
        doThrow(RuntimeException.class).when(iProjectUserRepository).deleteById(any(ProjectUserId.class));

        assertThrows(RuntimeException.class, () -> projectUserRepository.deleteAllProjectUsersForProjectId(PROJECT_ID));
        verify(iProjectUserRepository).findProjectUserByProjectIdContains(PROJECT_ID);
        verify(iProjectUserRepository).deleteById(any(ProjectUserId.class));
    }


    private ProjectUser getProjectUser() {
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(USER_ID);
        projectUser.setProjectId(PROJECT_ID);
        return projectUser;
    }
}
