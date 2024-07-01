package com.tasktide.projectServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.projectServices.model.ProjectUser;
import com.tasktide.projectServices.service.ProjectUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProjectUserControllerTest {
    private static final String PROJECT_ID = "test_projectUser_id";
    private static final String USER_ID = "test_user_id";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private ProjectUserService projectUserService;

    @InjectMocks
    private ProjectUserController projectUserController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(projectUserController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getProjectUsersByUserId_success() throws Exception {

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(USER_ID);
        projectUser.setProjectId(PROJECT_ID);
        when(projectUserService.getProjectUsersByUserId(USER_ID)).thenReturn(List.of(projectUser));

        MvcResult result = mockMvc.perform(get("/project-user/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<ProjectUser> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getProjectId(), projectUser.getProjectId());
        assertEquals(response.getFirst().getUserId(), projectUser.getUserId());
        verify(projectUserService).getProjectUsersByUserId(USER_ID);
    }


    @Test
    public void getProjectUsersByUserId_exception() throws Exception {

        when(projectUserService.getProjectUsersByUserId(USER_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/project-user/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectUserService).getProjectUsersByUserId(USER_ID);
    }


    @Test
    public void getAllProjectUsers_success() throws Exception {

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(USER_ID);
        projectUser.setProjectId(PROJECT_ID);
        when(projectUserService.getAllProjectUsers()).thenReturn(List.of(projectUser));

        MvcResult result = mockMvc.perform(get("/project-user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<ProjectUser> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getProjectId(), projectUser.getProjectId());
        assertEquals(response.getFirst().getUserId(), projectUser.getUserId());

        verify(projectUserService).getAllProjectUsers();
    }


    @Test
    public void getAllProjectUsers_exception() throws Exception {

        when(projectUserService.getAllProjectUsers()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/project-user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectUserService).getAllProjectUsers();
    }


    @Test
    public void addNewProjectUser_success() throws Exception {

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(USER_ID);
        projectUser.setProjectId(PROJECT_ID);
        when(projectUserService.addNewProjectUser(any())).thenReturn(projectUser);

        MvcResult result = mockMvc.perform(post("/project-user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectUser))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        ProjectUser response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getProjectId(), projectUser.getProjectId());
        assertEquals(response.getUserId(), projectUser.getUserId());

        verify(projectUserService).addNewProjectUser(any());
    }


    @Test
    public void addNewProjectUser_exception() throws Exception {

        when(projectUserService.addNewProjectUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/project-user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectUser()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectUserService).addNewProjectUser(any());
    }

    @Test
    public void removeProjectUser_success() throws Exception {

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(USER_ID);
        projectUser.setProjectId(PROJECT_ID);
        when(projectUserService.removeProjectUser(USER_ID, PROJECT_ID)).thenReturn(projectUser);

        MvcResult result = mockMvc.perform(delete("/project-user/?projectId=" + PROJECT_ID+"&userId=" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        ProjectUser response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getProjectId(), projectUser.getProjectId());
        assertEquals(response.getUserId(), projectUser.getUserId());

        verify(projectUserService).removeProjectUser(USER_ID, PROJECT_ID);
    }


    @Test
    public void removeProjectUser_exception() throws Exception {

        when(projectUserService.removeProjectUser(USER_ID, PROJECT_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/project-user/?projectId=" + PROJECT_ID+"&userId=" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectUserService).removeProjectUser(USER_ID, PROJECT_ID);
    }


    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(ProjectServicesAdvice.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new ProjectServicesAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
