package com.tasktide.calendarServices.service;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    EventRepository eventRepository;
    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventByEventId(String eventId) {
        return eventRepository.findEventById(eventId);
    }

    public List<Event> getEventsByUserId(String userId) {
        return eventRepository.findEventsByUserId(userId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllEvents();
    }
    public Event addNewEvent(Event event) {
        return eventRepository.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event removeEvent(String eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
