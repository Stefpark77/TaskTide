package com.tasktide.calendarServices.repository.interfaces;

import com.tasktide.calendarServices.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface IEventRepository extends CrudRepository<Event, String> {
    Iterable<Event> findEventsByUserIdContainsOrderByDateAsc(String userId);
}
