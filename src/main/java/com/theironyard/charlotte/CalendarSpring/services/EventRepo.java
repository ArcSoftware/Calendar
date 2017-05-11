package com.theironyard.charlotte.CalendarSpring.services;

import com.theironyard.charlotte.CalendarSpring.entities.Event;
import com.theironyard.charlotte.CalendarSpring.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jake on 5/11/17.
 */
public interface EventRepo extends CrudRepository<Event, Integer> {
    List<Event> findAllByOrderByDateTimeDesc();
    List<Event> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findAllByEndTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByUser(User user);
}