package com.agrix.agrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agrix.agrix.model.User;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // signup.html
    }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        if(userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        User newUser = new User(username, password);
        userRepository.save(newUser);
        model.addAttribute("message", "Signup successful! Please login.");
        return "login";
    }
}
