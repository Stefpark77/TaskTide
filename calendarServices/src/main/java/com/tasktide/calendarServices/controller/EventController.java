package com.tasktide.calendarServices.controller;

import com.tasktide.calendarServices.model.Event;
import com.tasktide.calendarServices.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@Slf4j
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
public class EventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public Event getEventByEventId(@PathVariable String eventId) {
        log.info("Getting event information for event id {}", eventId);
        return eventService.getEventByEventId(eventId);
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getEventsByUserId(@PathVariable String userId) {
        log.info("Getting events information for user id {}", userId);
        return eventService.getEventsByUserId(userId);
    }

    @GetMapping("/userId/{userId}/day/{day}")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getEventsByUserIdAndDay(@PathVariable String userId, @PathVariable Instant day) {
        log.info("Getting events information for user id {} for the day {}", userId, day);
        return eventService.getEventsByUserIdAndDay(userId, day);
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'userRead', 'adminRead'})")
    public List<Event> getAllEvents() {
        log.info("Getting all events' information");
        return eventService.getAllEvents();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'userCreate', 'adminCreate'})")
    public Event addNewEvent(@RequestBody Event event) {
        log.info("Adding a new event with name {}", event.getName());
        return eventService.addNewEvent(event);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'userUpdate', 'adminUpdate'})")
    public Event updateEvent(@RequestBody Event event) {
        log.info("Updating existing event information for event id {}", event.getId());
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'userDelete', 'adminDelete'})")
    public Event removeEvent(@RequestParam String eventId) {
        log.info("Removing existing event information for event id {}", eventId);
        return eventService.removeEvent(eventId);
    }
}
