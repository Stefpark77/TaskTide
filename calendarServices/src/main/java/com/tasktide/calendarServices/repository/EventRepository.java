package com.tasktide.calendarServices.repository;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.repository.interfaces.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;


@Repository
public class EventRepository{
    private final IEventRepository iEventRepository;

    @Autowired
    public EventRepository(IEventRepository iEventRepository) {
        this.iEventRepository = iEventRepository;
    }

    public Event findEventById(String id) {
        return iEventRepository.findById(id).orElse(null);
    }

    public List<Event> findEventsByUserId(String userId) {
        return (List<Event>) iEventRepository.findEventsByUserIdContains(userId);
    }

    public List<Event> findAllEvents() {
        return (List<Event>) iEventRepository.findAll();
    }
    public Event createEvent(Event event) {
        return iEventRepository.save(event);
    }

    public Event save(Event event) {
        Event newEvent = findEventById(event.getId());
        if(StringUtils.hasLength(event.getName()))
            newEvent.setName(event.getName());
        if(StringUtils.hasLength(event.getDescription()))
            newEvent.setDescription(event.getDescription());
        if(event.getDate() != null)
            newEvent.setDate(event.getDate());
        if(event.getEveryMonth() != null)
            newEvent.setEveryMonth(event.getEveryMonth());
        if(event.getEveryYear() != null)
            newEvent.setEveryYear(event.getEveryYear());
        newEvent.setUpdatedDate(Instant.now());
        iEventRepository.save(newEvent);
        return newEvent;
    }

    public Event deleteEvent(String id) {
        Event event = findEventById(id);
        if(event == null) {
            return null;
        }
        iEventRepository.deleteById(id);
        return event;
    }
}
