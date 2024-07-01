package com.tasktide.taskServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.taskServices.model.TaskDependency;
import com.tasktide.taskServices.service.TaskDependencyService;
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
public class TaskDependencyControllerTest {
    private static final String TASK_ID = "test_task_id";
    private static final String DEPENDING = "test_depending_id";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private TaskDependencyService taskDependencyService;

    @InjectMocks
    private TaskDependencyController taskDependencyController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(taskDependencyController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getTaskDependenciesByTaskId_success() throws Exception {

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        when(taskDependencyService.getTaskDependenciesByTaskId(TASK_ID)).thenReturn(List.of(taskDependency));

        MvcResult result = mockMvc.perform(get("/task-dependency/" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<TaskDependency> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getTaskId(), taskDependency.getTaskId());
        verify(taskDependencyService).getTaskDependenciesByTaskId(TASK_ID);
    }


    @Test
    public void getTaskDependenciesByTaskId_exception() throws Exception {

        when(taskDependencyService.getTaskDependenciesByTaskId(TASK_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task-dependency/" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).getTaskDependenciesByTaskId(TASK_ID);
    }

    @Test
    public void getTaskDependenciesByDependsOnId_success() throws Exception {

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        when(taskDependencyService.getTaskDependenciesByDependsOnId(DEPENDING)).thenReturn(List.of(taskDependency));

        MvcResult result = mockMvc.perform(get("/task-dependency/depends-on/" + DEPENDING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<TaskDependency> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getTaskId(), taskDependency.getTaskId());
        verify(taskDependencyService).getTaskDependenciesByDependsOnId(DEPENDING);
    }


    @Test
    public void getTaskDependenciesByDependsOnId_exception() throws Exception {

        when(taskDependencyService.getTaskDependenciesByDependsOnId(DEPENDING)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task-dependency/depends-on/" + DEPENDING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).getTaskDependenciesByDependsOnId(DEPENDING);
    }


    @Test
    public void getAllTaskDependencies_success() throws Exception {

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        when(taskDependencyService.getAllTaskDependencies()).thenReturn(List.of(taskDependency));

        MvcResult result = mockMvc.perform(get("/task-dependency/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<TaskDependency> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getTaskId(), taskDependency.getTaskId());

        verify(taskDependencyService).getAllTaskDependencies();
    }


    @Test
    public void getAllTaskDependencies_exception() throws Exception {

        when(taskDependencyService.getAllTaskDependencies()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task-dependency/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).getAllTaskDependencies();
    }


    @Test
    public void addNewTaskDependency_success() throws Exception {

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        taskDependency.setDependsOnId(TASK_ID);
        when(taskDependencyService.addNewTaskDependency(any())).thenReturn(taskDependency);

        MvcResult result = mockMvc.perform(post("/task-dependency/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDependency))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        TaskDependency response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getTaskId(), taskDependency.getTaskId());
        assertEquals(response.getDependsOnId(), taskDependency.getDependsOnId());

        verify(taskDependencyService).addNewTaskDependency(any());
    }


    @Test
    public void addNewTaskDependency_exception() throws Exception {

        when(taskDependencyService.addNewTaskDependency(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/task-dependency/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TaskDependency()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).addNewTaskDependency(any());
    }

    @Test
    public void removeTaskDependency_success() throws Exception {

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTaskId(TASK_ID);
        taskDependency.setDependsOnId(DEPENDING);
        when(taskDependencyService.removeTaskDependency(TASK_ID, DEPENDING)).thenReturn(taskDependency);

        MvcResult result = mockMvc.perform(delete("/task-dependency/?taskId=" + TASK_ID + "&dependsOnId=" + DEPENDING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        TaskDependency response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getTaskId(), taskDependency.getTaskId());

        verify(taskDependencyService).removeTaskDependency(TASK_ID, DEPENDING);
    }


    @Test
    public void removeTaskDependency_exception() throws Exception {

        when(taskDependencyService.removeTaskDependency(TASK_ID, DEPENDING)).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/task-dependency/?taskId=" + TASK_ID + "&dependsOnId=" + DEPENDING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).removeTaskDependency(TASK_ID, DEPENDING);
    }


    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(TaskServicesAdvice.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new TaskServicesAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
