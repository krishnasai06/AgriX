package com.agrix.agrix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping({"/", "/index"})
    public String index(HttpSession session) {
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "index"; // index.html
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "dashboard"; // dashboard.html
    }

    @GetMapping("/history")
    public String history(HttpSession session) {
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "history"; // dashboard.html
    }
}

