package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Event;
import com.example.youthcenter.models.Theme;
import com.example.youthcenter.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
public class EventController {

    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @ModelAttribute("themes")
    public Theme[] getThemes() {
        return Theme.values();
    }

    @GetMapping("/my/events")
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "for-manager/all-events";
    }

    @GetMapping("/my/events/{evId}")
    public String getEventById(@PathVariable(value = "evId") Long evId, Model model) {
        model.addAttribute("event", eventService.getEventById(evId));
        return "for-manager/one-event";
    }

    @PostMapping("/my/events")
    public String deleteEvent(){
        return "redirect:/my/events";
    }

    @GetMapping("/my/events/new")
    public String addEventForm(){
        return "for-manager/event-add";
    }
    @PostMapping("/my/events/new")
    public String addingEvent(Event event, Principal principal){
        eventService.addEvent(event, principal);
        return "redirect:/my/events";
    }

}
