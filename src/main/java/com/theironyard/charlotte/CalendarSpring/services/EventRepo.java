package com.theironyard.charlotte.CalendarSpring.services;

import com.theironyard.charlotte.CalendarSpring.entities.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jake on 5/11/17.
 */
public interface EventRepo extends CrudRepository<Event, Integer> {
    List<Event> findAllByOrderByDateTimeDesc();
}