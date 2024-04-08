package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Gender;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @ModelAttribute("genders")
    public Gender[] getGenders(){
        return Gender.values();
    }
    @GetMapping("/my/users")
    public String users(@RequestParam(name = "query", required = false) String query, Model model){
        List<User> users;
//        if (query != null && !query.isEmpty()) {
//            users = userRepository.findByFullNameContainingIgnoreCase(query);
//        } else {
        users = userRepository.findAll();
//        }
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/registration")
    public String registration(Model model){
        return "regForm";
    }
    @PostMapping("/registration")
    public String authorization(User user, RedirectAttributes redirectAttributes){
//        if (userRepository.existsByEmail(email)) {
//            redirectAttributes.addFlashAttribute("error", "Користувач з цією електронною поштою вже існує");
//            return "redirect:/reg";
//        }
        userService.createUser(user);
        return "redirect:/my/users";
    }
}
