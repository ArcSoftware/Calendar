package com.theironyard.charlotte.CalendarSpring.services;

import com.theironyard.charlotte.CalendarSpring.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jake on 5/11/17.
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}