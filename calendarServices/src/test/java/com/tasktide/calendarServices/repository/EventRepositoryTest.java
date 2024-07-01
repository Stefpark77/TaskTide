package com.tasktide.calendarServices.repository;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.repository.interfaces.IEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventRepositoryTest {
    @Mock
    private IEventRepository iEventRepository;

    @InjectMocks
    private EventRepository eventRepository;

    private static final String EVENT_ID = "test_event_id";
    private static final String USER_ID = "test_user_id";


    @Test
    public void findEventById_success() {

        Event event = getEvent();
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));

        Event response = eventRepository.findEventById(EVENT_ID);

        assertNotNull(response);
        assertEquals(response, event);
        verify(iEventRepository).findById(EVENT_ID);
    }

    @Test
    public void findEventById_exception() {

        when(iEventRepository.findById(EVENT_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventRepository.findEventById(EVENT_ID));
        verify(iEventRepository).findById(EVENT_ID);
    }
    
    @Test
    public void findEventByUserId_success() {

        Event event = getEvent();
        when(iEventRepository.findEventsByUserIdContainsOrderByDateAsc(USER_ID)).thenReturn(List.of(event));

        List<Event> response = eventRepository.findEventsByUserId(USER_ID);

        assertNotNull(response);
        assertEquals(response.getFirst(), event);
        verify(iEventRepository).findEventsByUserIdContainsOrderByDateAsc(USER_ID);
    }

    @Test
    public void findEventByUserId_exception() {

        when(iEventRepository.findEventsByUserIdContainsOrderByDateAsc(USER_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventRepository.findEventsByUserId(USER_ID));
        verify(iEventRepository).findEventsByUserIdContainsOrderByDateAsc(USER_ID);
    }


    @Test
    public void findAllEvents_success() {

        Event event = getEvent();
        when(iEventRepository.findAll()).thenReturn(List.of(event));

        List<Event> response = eventRepository.findAllEvents();

        assertNotNull(response);
        assertEquals(response.getFirst(), event);
        verify(iEventRepository).findAll();
    }

    @Test
    public void findAllEvents_exception() {

        when(iEventRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventRepository.findAllEvents());
        verify(iEventRepository).findAll();
    }


    @Test
    public void createEvent_success() {

        Event event = getEvent();
        when(iEventRepository.save(any())).thenReturn(event);

        Event response = eventRepository.createEvent(event);

        assertNotNull(response);
        assertEquals(response, event);
        verify(iEventRepository).save(any());
    }

    @Test
    public void createEvent_exception() {

        when(iEventRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventRepository.createEvent(getEvent()));
        verify(iEventRepository).save(any());
    }


    @Test
    public void updateEvent_success() {

        Event event = getEvent();
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        when(iEventRepository.save(any())).thenReturn(event);

        Event response = eventRepository.updateEvent(event);

        assertNotNull(response);
        assertEquals(response, event);
        verify(iEventRepository).findById(EVENT_ID);
        verify(iEventRepository).save(any());
    }

    @Test
    public void updateEvent_exception() {
        Event event = getEvent();
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        when(iEventRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> eventRepository.updateEvent(getEvent()));
        verify(iEventRepository).findById(EVENT_ID);
        verify(iEventRepository).save(any());
    }


    @Test
    public void deleteEvent_success() {

        Event event = getEvent();
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));

        Event response = eventRepository.deleteEvent(EVENT_ID);

        assertNotNull(response);
        assertEquals(response, event);
        verify(iEventRepository).findById(EVENT_ID);
        verify(iEventRepository).deleteById(EVENT_ID);
    }

    @Test
    public void deleteEvent_success_nullEvent() {
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.empty());

        Event response =  eventRepository.deleteEvent(EVENT_ID);

        assertNull(response);
        verify(iEventRepository).findById(EVENT_ID);
        verify(iEventRepository, times(0)).deleteById(EVENT_ID);
    }

    @Test
    public void deleteEvent_exception() {
        Event event = getEvent();
        when(iEventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        doThrow(RuntimeException.class).when(iEventRepository).deleteById(EVENT_ID);

        assertThrows(RuntimeException.class, () -> eventRepository.deleteEvent(EVENT_ID));
        verify(iEventRepository).findById(EVENT_ID);
        verify(iEventRepository).deleteById(EVENT_ID);
    }


    private Event getEvent() {
        Event event = new Event();
        event.setId(EVENT_ID);
        return event;
    }
}
