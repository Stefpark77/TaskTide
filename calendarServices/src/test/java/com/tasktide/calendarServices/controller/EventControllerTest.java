package com.tasktide.calendarServices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.service.EventService;
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
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {
    private static final String EVENT_ID = "test_event_id";
    private static final String USER_ID = "test_user_id";
    private static final Instant DAY = Instant.now();
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_RESPONSE_TEST = "test";

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, HEADER_RESPONSE_TEST);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setHandlerExceptionResolvers(createExceptionResolver()).build();
    }


    @Test
    public void getEventByEventId_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.getEventByEventId(EVENT_ID)).thenReturn(event);

        MvcResult result = mockMvc.perform(get("/event/" + EVENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        Event response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), event.getId());
        verify(eventService).getEventByEventId(EVENT_ID);
    }


    @Test
    public void getEventByEventId_exception() throws Exception {

        when(eventService.getEventByEventId(EVENT_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/event/" + EVENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).getEventByEventId(EVENT_ID);
    }


    @Test
    public void getEventByUserId_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.getEventsByUserId(USER_ID)).thenReturn(List.of(event));

        MvcResult result = mockMvc.perform(get("/event/userId/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Event> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), event.getId());

        verify(eventService).getEventsByUserId(USER_ID);
    }


    @Test
    public void getEventByUserId_exception() throws Exception {

        when(eventService.getEventsByUserId(USER_ID)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/event/userId/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).getEventsByUserId(USER_ID);
    }


    @Test
    public void getEventsByUserIdAndDay_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.getEventsByUserIdAndDay(USER_ID, DAY)).thenReturn(List.of(event));

        MvcResult result = mockMvc.perform(get("/event/userId/" + USER_ID + "/day/" + DAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();


        List<Event> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), event.getId());

        verify(eventService).getEventsByUserIdAndDay(USER_ID, DAY);
    }


    @Test
    public void getEventsByUserIdAndDay_exception() throws Exception {

        when(eventService.getEventsByUserIdAndDay(USER_ID, DAY)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/event/userId/" + USER_ID + "/day/" + DAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).getEventsByUserIdAndDay(USER_ID, DAY);
    }

    @Test
    public void getAllEvents_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.getAllEvents()).thenReturn(List.of(event));

        MvcResult result = mockMvc.perform(get("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        List<Event> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), event.getId());

        verify(eventService).getAllEvents();
    }


    @Test
    public void getAllEvents_exception() throws Exception {

        when(eventService.getAllEvents()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).getAllEvents();
    }


    @Test
    public void addNewEvent_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.addNewEvent(any())).thenReturn(event);

        MvcResult result = mockMvc.perform(post("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Event response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), event.getId());

        verify(eventService).addNewEvent(any());
    }


    @Test
    public void addNewEvent_exception() throws Exception {

        when(eventService.addNewEvent(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Event()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).addNewEvent(any());
    }

    @Test
    public void updateEvent_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.updateEvent(any())).thenReturn(event);

        MvcResult result = mockMvc.perform(put("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Event response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), event.getId());

        verify(eventService).updateEvent(any());
    }


    @Test
    public void updateEvent_exception() throws Exception {

        when(eventService.updateEvent(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Event()))
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).updateEvent(any());
    }

    @Test
    public void removeEvent_success() throws Exception {

        Event event = new Event();
        event.setId(EVENT_ID);
        when(eventService.removeEvent(any())).thenReturn(event);

        MvcResult result = mockMvc.perform(delete("/event/?eventId=" + EVENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk()).andReturn();

        Event response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(response);
        assertEquals(response.getId(), event.getId());

        verify(eventService).removeEvent(any());
    }


    @Test
    public void removeEvent_exception() throws Exception {

        when(eventService.removeEvent(any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/event/?eventId=" + EVENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isInternalServerError()).andReturn();
        verify(eventService).removeEvent(any());
    }


    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(CalendarServicesAdvice.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new CalendarServicesAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
