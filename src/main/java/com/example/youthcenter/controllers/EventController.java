package com.example.youthcenter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @GetMapping("/event/add")
    public String addEventForm(){
        return "for-manager/event-add";
    }

}
