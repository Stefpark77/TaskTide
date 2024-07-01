package com.tasktide.calendarServices.service;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.model.RecurringTime;
import com.tasktide.calendarServices.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private static final String EVENT_ID = "test_event_id";
    private static final String USER_ID = "test_user_id";
    private static final Instant DAY = Instant.now();


    @Test
    public void getEventByEventId_success() {

        Event event = getEvent();
        when(eventRepository.findEventById(EVENT_ID)).thenReturn(event);

        Event response = eventService.getEventByEventId(EVENT_ID);

        assertNotNull(response);
        assertEquals(response, event);
        verify(eventRepository).findEventById(EVENT_ID);
    }

    @Test
    public void getEventByEventId_exception() {

        when(eventRepository.findEventById(EVENT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.getEventByEventId(EVENT_ID));
        verify(eventRepository).findEventById(EVENT_ID);
    }


    @Test
    public void getEventByUserId_success() {

        Event event = getEvent();
        when(eventRepository.findEventsByUserId(USER_ID)).thenReturn(List.of(event));

        List<Event> response = eventService.getEventsByUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), event);
        verify(eventRepository).findEventsByUserId(USER_ID);
    }

    @Test
    public void getEventByUserId_exception() {

        when(eventRepository.findEventsByUserId(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.getEventsByUserId(USER_ID));
        verify(eventRepository).findEventsByUserId(USER_ID);
    }


    @Test
    public void getAllEvents_success() {

        Event event = getEvent();
        when(eventRepository.findAllEvents()).thenReturn(List.of(event));

        List<Event> response = eventService.getAllEvents();

        assertNotNull(response);
        assertEquals(response.getFirst(), event);
        verify(eventRepository).findAllEvents();
    }

    @Test
    public void getAllEvents_exception() {

        when(eventRepository.findAllEvents()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.getAllEvents());
        verify(eventRepository).findAllEvents();
    }


    @Test
    public void addNewEvent_success() {

        Event event = getEvent();
        when(eventRepository.createEvent(any())).thenReturn(event);

        Event response = eventService.addNewEvent(event);

        assertNotNull(response);
        assertEquals(response, event);
        verify(eventRepository).createEvent(any());
    }

    @Test
    public void addNewEvent_exception() {

        when(eventRepository.createEvent(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.addNewEvent(getEvent()));
        verify(eventRepository).createEvent(any());
    }


    @Test
    public void updateEvent_success() {

        Event event = getEvent();
        when(eventRepository.updateEvent(any())).thenReturn(event);

        Event response = eventService.updateEvent(event);

        assertNotNull(response);
        assertEquals(response, event);
        verify(eventRepository).updateEvent(any());
    }

    @Test
    public void updateEvent_exception() {
        when(eventRepository.updateEvent(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.updateEvent(getEvent()));
        verify(eventRepository).updateEvent(any());
    }


    @Test
    public void deleteEvent_success() {

        Event event = getEvent();
        when(eventRepository.deleteEvent(EVENT_ID)).thenReturn(event);

        Event response = eventService.removeEvent(EVENT_ID);

        assertNotNull(response);
        assertEquals(response, event);
        verify(eventRepository).deleteEvent(EVENT_ID);
    }

    @Test
    public void deleteEvent_exception() {
        doThrow(RuntimeException.class).when(eventRepository).deleteEvent(EVENT_ID);

        assertThrows(RuntimeException.class, () -> eventService.removeEvent(EVENT_ID));
        verify(eventRepository).deleteEvent(EVENT_ID);
    }


    @Test
    public void getEventsByUserIdAndDay_success() {

        when(eventRepository.findEventsByUserId(USER_ID)).thenReturn(getAllRecurringEvents());

        List<Event> response = eventService.getEventsByUserIdAndDay(USER_ID, DAY);

        assertNotNull(response);
        assertEquals(6, response.size());
        verify(eventRepository).findEventsByUserId(USER_ID);
    }

    @Test
    public void getEventsByUserIdAndDay_exception() {

        when(eventRepository.findEventsByUserId(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventService.getEventsByUserIdAndDay(USER_ID, DAY));
        verify(eventRepository).findEventsByUserId(USER_ID);
    }


    private Event getEvent() {
        Event event = new Event();
        event.setId(EVENT_ID);
        return event;
    }

    private List<Event> getAllRecurringEvents() {
        List<Event> events = new ArrayList<>();

        Event onceEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setEndDate(Instant.now().plus(5, ChronoUnit.DAYS));
        onceEvent.setRecurringTime(RecurringTime.ONCE);

        Event dailyEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setRecurringTime(RecurringTime.DAILY);


        Event weeklyEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setRecurringTime(RecurringTime.WEEKLY);

        Event biweeklyEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setRecurringTime(RecurringTime.BIWEEKLY);

        Event monthlyEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setRecurringTime(RecurringTime.MONTHLY);

        Event annualEvent = getEvent();
        onceEvent.setDate(Instant.now());
        onceEvent.setRecurringTime(RecurringTime.ANNUAL);

        events.add(onceEvent);
        events.add(dailyEvent);
        events.add(weeklyEvent);
        events.add(biweeklyEvent);
        events.add(monthlyEvent);
        events.add(annualEvent);
        return events;
    }


}
