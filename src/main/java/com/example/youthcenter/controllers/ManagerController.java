package com.example.youthcenter.controllers;

import com.example.youthcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ManagerController {
    private final UserService userService;

    @Autowired
    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my/apps")
    public String showApps(Model model) {
        model.addAttribute("usersApps", userService.getUsersNonActive());
        return "for-manager/users-apps";
    }
    @GetMapping("/my/apps/{userId}")
    public String showOneUserApp(Model model, @PathVariable(value = "userId") Long userId) {
        model.addAttribute("userApp", userService.getUserById(userId));
        return "for-manager/one-user-app";
    }
}
