package com.example.youthcenter.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){return "about";}

}
