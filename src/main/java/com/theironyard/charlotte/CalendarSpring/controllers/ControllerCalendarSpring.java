package com.theironyard.charlotte.CalendarSpring.controllers;

import com.theironyard.charlotte.CalendarSpring.entities.Event;
import com.theironyard.charlotte.CalendarSpring.entities.User;
import com.theironyard.charlotte.CalendarSpring.services.EventRepo;
import com.theironyard.charlotte.CalendarSpring.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class ControllerCalendarSpring {
    LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @Autowired
    EventRepo events;

    @Autowired
    UserRepo users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        List<Event> eventEntities = events.findAllByOrderByDateTimeDesc(); //get all events and order by time ascending
        List<Event> userEvents = events.findByUser(users.findFirstByName(userName));

        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("userObject", user);
            model.addAttribute("now", currentTime);
            //truncated only puts the time down to the seconds not the milliseconds.

        }
        model.addAttribute("events", eventEntities);
        model.addAttribute("userEvents", userEvents);
        return "home";
    }

    @RequestMapping(path = "/create-event", method = RequestMethod.POST)
    public String createEvent(HttpSession session, String description, String dateTime, String endTime) {
        String userName = (String) session.getAttribute("userName");
        LocalDateTime startTime = LocalDateTime.parse(dateTime);
        LocalDateTime eventEndTime = LocalDateTime.parse(endTime);
        List<Event> timeCheck = events.findAllByDateTimeBetween(startTime, eventEndTime);
        List<Event> timeCheckEnd = events.findAllByEndTimeBetween(startTime, eventEndTime);
        if (userName != null) {
            if (currentTime.isBefore(startTime) && startTime.isBefore(eventEndTime) && timeCheck.isEmpty() &&
                   timeCheckEnd.isEmpty()) {
                Event event = new Event(description, startTime, eventEndTime, users.findFirstByName(userName));
                events.save(event);
            } else if (startTime.isBefore(eventEndTime)) {
                System.out.println("Entered time of " + startTime + " is before " + eventEndTime);
            }

        } return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name) {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name);
            users.save(user);
        }
        session.setAttribute("userName", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}