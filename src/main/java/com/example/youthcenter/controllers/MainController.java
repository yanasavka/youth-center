package com.example.youthcenter.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/about")
    public String about(Model model) {
        return "for-guests/about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        return "for-guests/contacts";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        return "hello";
    }

    @GetMapping("/my")
    public String myHome(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "my-home";
    }
}
