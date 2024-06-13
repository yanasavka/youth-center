package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Gender;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ParticipantController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public ParticipantController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
//    @GetMapping
//    public String getSignOutPrePage(Model model) {
//
//
//        return "pre-logout";
//    }

    @ModelAttribute("genders")
    public Gender[] getGenders(){
        return Gender.values();
    }

    @GetMapping("/my/users")
    public String getParticipants(@RequestParam(name = "query", required = false) String query,
                                  Model model, Principal principal) {
        List<User> users;
        if (query != null && !query.isEmpty()) {
            users = userRepository.findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCaseOrFullNameStartingWithIgnoreCase(query, query, query);
        } else {
            users = userRepository.findAll();
        }
        model.addAttribute("users", users);
        model.addAttribute("user", principal);
        return "for-participants/users";
    }
    @GetMapping("/registration")
    public String registrationForm(Model model){
        return "regForm";
    }

    @PostMapping("/registration")
    public String registering(User user, Model model){
        if (!userService.createUser(user)) {
            model.addAttribute("error", "Користувач з електронною поштою " + user.getEmail() + " вже існує");
            return "regForm";
        }
        return "redirect:/my";
    }
}
