package com.theironyard.charlotte.CalendarSpring.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime dateTime;

    @Column(nullable = false)
    LocalDateTime endTime;

    @ManyToOne
    User user;

    public Event() {
    }

    public Event(String description, LocalDateTime dateTime, LocalDateTime endTime, User user) {
        this.description = description;
        this.dateTime = dateTime;
        this.endTime = endTime;
        this.user = user;

    }
}