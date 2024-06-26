package com.tasktide.calendarServices.service;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event getEventByEventId(String eventId) {
        return eventRepository.findEventById(eventId);
    }

    public List<Event> getEventsByUserId(String userId) {
        return eventRepository.findEventsByUserId(userId);
    }

    public List<Event> getEventsByUserIdAndDay(String userId, Instant day) {
        return eventRepository.findEventsByUserId(userId).stream()
                .filter(event -> isEventOccurring(event, day))
                .sorted(Comparator.comparing(x -> x.getDate().atZone(ZoneOffset.UTC).getHour() * 60 + x.getDate().atZone(ZoneOffset.UTC).getMinute()))
                .toList();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllEvents();
    }

    public Event addNewEvent(Event event) {
        return eventRepository.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }

    public Event removeEvent(String eventId) {
        return eventRepository.deleteEvent(eventId);
    }

    private Boolean isEventOccurring(Event event, Instant day) {
        LocalDate eventDate = event.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = event.getEndDate().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dayDate = day.atZone(ZoneId.systemDefault()).toLocalDate();
        switch (event.getRecurringTime()) {
            case ONCE:
                return dayDate.isAfter(eventDate) && dayDate.isBefore(endDate) || dayDate.isEqual(eventDate) || dayDate.isEqual(endDate);
            case DAILY:
                return (dayDate.isAfter(eventDate) || dayDate.isEqual(eventDate));
            case WEEKLY:
                return (dayDate.isAfter(eventDate) || dayDate.isEqual(eventDate)) && dayDate.get(ChronoField.DAY_OF_WEEK) == eventDate.get(ChronoField.DAY_OF_WEEK);
            case BIWEEKLY:
                return (dayDate.isAfter(eventDate) || dayDate.isEqual(eventDate)) && ChronoUnit.DAYS.between(eventDate, dayDate) % 14 == 0;
            case MONTHLY:
                return (dayDate.isAfter(eventDate) || dayDate.isEqual(eventDate)) && dayDate.getDayOfMonth() == eventDate.getDayOfMonth();
            case ANNUAL:
                return (dayDate.isAfter(eventDate) || dayDate.isEqual(eventDate)) && dayDate.getDayOfMonth() == eventDate.getDayOfMonth()
                        && dayDate.getMonth() == eventDate.getMonth();
            default:
                return false;
        }
    }
}
