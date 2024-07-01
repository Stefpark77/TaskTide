package com.tasktide.userServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
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
public class PublicUserControllerTest {
    private static final String USER_ID = "test_user_id";
    private static final String PROJECT_ID = "test_project_id";
    private static final String USERNAME = "test_username";

    @Mock
    private UserService userService;

    @InjectMocks
    private PublicUserController publicUserController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(publicUserController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getUserByUserId_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.getUserByUserId(USER_ID)).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/user/public/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        User response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), user.getId());
        verify(userService).getUserByUserId(USER_ID);
    }


    @Test
    public void getUserByUserId_exception() throws Exception {

        when(userService.getUserByUserId(USER_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/user/public/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).getUserByUserId(USER_ID);
    }


    @Test
    public void getUserByUsername_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.getUserByUsername(USERNAME)).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/user/public/username/" + USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        User response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), user.getId());

        verify(userService).getUserByUsername(USERNAME);
    }


    @Test
    public void getUserByUsername_exception() throws Exception {

        when(userService.getUserByUsername(USERNAME)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/user/public/username/" + USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).getUserByUsername(USERNAME);
    }


    @Test
    public void getUsersByProjectId_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.getUsersByProjectId(PROJECT_ID)).thenReturn(List.of(user));

        MvcResult result = mockMvc.perform(get("/user/public/projectId/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), user.getId());

        verify(userService).getUsersByProjectId(PROJECT_ID);
    }


    @Test
    public void getUsersByProjectId_exception() throws Exception {

        when(userService.getUsersByProjectId(PROJECT_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/user/public/projectId/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).getUsersByProjectId(PROJECT_ID);
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(UserServicesAdvice.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new UserServicesAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
