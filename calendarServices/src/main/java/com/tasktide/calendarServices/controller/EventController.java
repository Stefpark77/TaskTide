package com.tasktide.calendarServices.controller;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Event getEventByEventId(@PathVariable String eventId) {
        return eventService.getEventByEventId(eventId);
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getEventsByUserId(@PathVariable String userId) {
        return eventService.getEventsByUserId(userId);
    }

    @GetMapping("/userId/{userId}/day/{day}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getEventsByUserIdAndDay(@PathVariable String userId,@PathVariable Instant day) {
        return eventService.getEventsByUserIdAndDay(userId, day);
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Event addNewEvent(@RequestBody Event event) {
        return eventService.addNewEvent(event);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Event updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Event removeEvent(@RequestParam String eventId) {
        return eventService.removeEvent(eventId);
    }
}
