package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Gender;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

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
    @GetMapping("/reg")
    public String registration(Model model){
        return "regForm";
    }
    @PostMapping("/reg")
    public String authorization(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String sex,
                                @RequestParam LocalDate dateOfBirth,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model, RedirectAttributes redirectAttributes){
        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Користувач з цією електронною поштою вже існує");
            return "redirect:/reg";
        }
        User user = new User();
        Gender gender = Gender.valueOf(sex);
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/users";
    }
}
