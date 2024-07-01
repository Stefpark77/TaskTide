package com.tasktide.userServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.userServices.model.User;
import com.tasktide.userServices.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final String USER_ID = "test_user_id";
    private static final String USERNAME = "test_username";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getUserByUserId_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.getUserByUserId(USER_ID)).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/user/" + USER_ID)
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

        mockMvc.perform(get("/user/" + USER_ID)
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

        MvcResult result = mockMvc.perform(get("/user/username/" + USERNAME)
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

        mockMvc.perform(get("/user/username/" + USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).getUserByUsername(USERNAME);
    }


    @Test
    public void getAllUsers_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.getAllUsers()).thenReturn(List.of(user));

        MvcResult result = mockMvc.perform(get("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), user.getId());

        verify(userService).getAllUsers();
    }


    @Test
    public void getAllUsers_exception() throws Exception {

        when(userService.getAllUsers()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).getAllUsers();
    }


    @Test
    public void addNewUser_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.addNewUser(any())).thenReturn(user);

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        User response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), user.getId());

        verify(userService).addNewUser(any());
    }


    @Test
    public void addNewUser_exception() throws Exception {

        when(userService.addNewUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).addNewUser(any());
    }

    @Test
    public void updateUser_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.updateUser(any())).thenReturn(user);

        MvcResult result = mockMvc.perform(put("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        User response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), user.getId());

        verify(userService).updateUser(any());
    }


    @Test
    public void updateUser_exception() throws Exception {

        when(userService.updateUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).updateUser(any());
    }

    @Test
    public void removeUser_success() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        when(userService.removeUser(any())).thenReturn(user);

        MvcResult result = mockMvc.perform(delete("/user/?userId=" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        User response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), user.getId());

        verify(userService).removeUser(any());
    }


    @Test
    public void removeUser_exception() throws Exception {

        when(userService.removeUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/user/?userId=" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(userService).removeUser(any());
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
