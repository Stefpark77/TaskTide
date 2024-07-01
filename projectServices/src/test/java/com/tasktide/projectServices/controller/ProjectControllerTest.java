package com.tasktide.projectServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.projectServices.model.Project;
import com.tasktide.projectServices.service.ProjectService;
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
public class ProjectControllerTest {
    private static final String PROJECT_ID = "test_project_id";
    private static final String USER_ID = "test_user_id";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectUserService projectUserService;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getProjectByProjectId_success() throws Exception {

        Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectService.getProjectByProjectId(PROJECT_ID)).thenReturn(project);

        MvcResult result = mockMvc.perform(get("/project/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        Project response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), project.getId());
        verify(projectService).getProjectByProjectId(PROJECT_ID);
    }


    @Test
    public void getProjectByProjectId_exception() throws Exception {

        when(projectService.getProjectByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/project/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectService).getProjectByProjectId(PROJECT_ID);
    }


    @Test
    public void getAllProjects_success() throws Exception {

        Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectService.getAllProjects()).thenReturn(List.of(project));

        MvcResult result = mockMvc.perform(get("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<Project> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), project.getId());

        verify(projectService).getAllProjects();
    }


    @Test
    public void getAllProjects_exception() throws Exception {

        when(projectService.getAllProjects()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectService).getAllProjects();
    }


    @Test
    public void addNewProject_success() throws Exception {

        Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectService.addNewProject(any())).thenReturn(project);

        MvcResult result = mockMvc.perform(post("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Project response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), project.getId());

        verify(projectService).addNewProject(any());
    }


    @Test
    public void addNewProject_exception() throws Exception {

        when(projectService.addNewProject(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Project()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectService).addNewProject(any());
    }

    @Test
    public void updateProject_success() throws Exception {

        Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectService.updateProject(any())).thenReturn(project);

        MvcResult result = mockMvc.perform(put("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Project response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), project.getId());

        verify(projectService).updateProject(any());
    }


    @Test
    public void updateProject_exception() throws Exception {

        when(projectService.updateProject(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/project/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Project()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectService).updateProject(any());
    }

    @Test
    public void removeProject_success() throws Exception {

        Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectService.removeProject(any())).thenReturn(project);

        MvcResult result = mockMvc.perform(delete("/project/?projectId=" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Project response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), project.getId());

        verify(projectUserService).removeAllProjectUsersForProjectId(any());
        verify(projectService).removeProject(any());
    }


    @Test
    public void removeProject_exception() throws Exception {

        when(projectService.removeProject(any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/project/?projectId=" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(projectUserService).removeAllProjectUsersForProjectId(any());
        verify(projectService).removeProject(any());
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
