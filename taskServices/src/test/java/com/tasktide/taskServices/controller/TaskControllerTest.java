package com.tasktide.taskServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.taskServices.model.Task;
import com.tasktide.taskServices.service.TaskDependencyService;
import com.tasktide.taskServices.service.TaskService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    private static final String TASK_ID = "test_task_id";
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private TaskService taskService;
    @Mock
    private TaskDependencyService taskDependencyService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getTaskByTaskId_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getTaskByTaskId(TASK_ID)).thenReturn(task);

        MvcResult result = mockMvc.perform(get("/task/" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        Task response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), task.getId());
        verify(taskService).getTaskByTaskId(TASK_ID);
    }


    @Test
    public void getTaskByTaskId_exception() throws Exception {

        when(taskService.getTaskByTaskId(TASK_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task/" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getTaskByTaskId(TASK_ID);
    }


    @Test
    public void getDependedTasksByTaskId_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getDependedTasksByTaskId(TASK_ID)).thenReturn(List.of(task));

        MvcResult result = mockMvc.perform(get("/task/" + TASK_ID + "/depends-on/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Task> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), task.getId());

        verify(taskService).getDependedTasksByTaskId(TASK_ID);
    }


    @Test
    public void getDependedTasksByTaskId_exception() throws Exception {

        when(taskService.getDependedTasksByTaskId(TASK_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task/" + TASK_ID + "/depends-on/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getDependedTasksByTaskId(TASK_ID);
    }


    @Test
    public void getDependingTasksByDependsOnId_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getDependingTasksByDependsOnId(TASK_ID)).thenReturn(List.of(task));

        MvcResult result = mockMvc.perform(get("/task/" + TASK_ID + "/depended-tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Task> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), task.getId());

        verify(taskService).getDependingTasksByDependsOnId(TASK_ID);
    }


    @Test
    public void getDependingTasksByDependsOnId_exception() throws Exception {

        when(taskService.getDependingTasksByDependsOnId(TASK_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task/" + TASK_ID + "/depended-tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getDependingTasksByDependsOnId(TASK_ID);
    }


    @Test
    public void getTasksByUserId_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getTasksByUserId(eq(USER_ID), any())).thenReturn(List.of(task));

        MvcResult result = mockMvc.perform(post("/task/userId/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Task> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), task.getId());

        verify(taskService).getTasksByUserId(eq(USER_ID), any());
    }


    @Test
    public void getTasksByUserId_exception() throws Exception {

        when(taskService.getTasksByUserId(eq(USER_ID), any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/task/userId/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getTasksByUserId(eq(USER_ID), any());
    }


    @Test
    public void getTasksByProjectId_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getTasksByProjectId(PROJECT_ID)).thenReturn(List.of(task));

        MvcResult result = mockMvc.perform(get("/task/projectId/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Task> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), task.getId());

        verify(taskService).getTasksByProjectId(PROJECT_ID);
    }


    @Test
    public void getTasksByProjectId_exception() throws Exception {

        when(taskService.getTasksByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task/projectId/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getTasksByProjectId(PROJECT_ID);
    }

    @Test
    public void getAllTasks_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.getAllTasks()).thenReturn(List.of(task));

        MvcResult result = mockMvc.perform(get("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<Task> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), task.getId());

        verify(taskService).getAllTasks();
    }


    @Test
    public void getAllTasks_exception() throws Exception {

        when(taskService.getAllTasks()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).getAllTasks();
    }


    @Test
    public void addNewTask_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.addNewTask(any())).thenReturn(task);

        MvcResult result = mockMvc.perform(post("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Task response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), task.getId());

        verify(taskService).addNewTask(any());
    }


    @Test
    public void addNewTask_exception() throws Exception {

        when(taskService.addNewTask(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).addNewTask(any());
    }

    @Test
    public void updateTask_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.updateTask(any())).thenReturn(task);

        MvcResult result = mockMvc.perform(put("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Task response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), task.getId());

        verify(taskService).updateTask(any());
    }


    @Test
    public void updateTask_exception() throws Exception {

        when(taskService.updateTask(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskService).updateTask(any());
    }

    @Test
    public void removeTask_success() throws Exception {

        Task task = new Task();
        task.setId(TASK_ID);
        when(taskService.removeTask(any())).thenReturn(task);

        MvcResult result = mockMvc.perform(delete("/task/?taskId=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Task response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), task.getId());

        verify(taskDependencyService).removeAllDependenciesByTaskId(TASK_ID);
        verify(taskService).removeTask(any());
    }


    @Test
    public void removeTask_exception() throws Exception {

        when(taskService.removeTask(any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/task/?taskId=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(taskDependencyService).removeAllDependenciesByTaskId(TASK_ID);
        verify(taskService).removeTask(TASK_ID);
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
