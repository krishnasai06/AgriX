package com.agrix.agrix.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrix.agrix.model.NeelluLog;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class NeelluController {

    @Autowired
    private NeelluRepository neelluRepository;

    @Autowired
    private SoilController soilController;

    private final Random random = new Random();

    // ✅ Save new irrigation log for current user
    @GetMapping("/neellu/save")
    public Map<String, String> saveNeelluLog(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            response.put("message", "⚠️ User not logged in!");
            return response;
        }

        Map<String, Object> status = soilController.getCurrentSoilStatusForUser(username);

        NeelluLog log = new NeelluLog();
        log.setUsername(username);
        log.setCity((String) status.getOrDefault("city", "N/A"));
        log.setMoisture((Integer) status.getOrDefault("value", 0));
        log.setStatus((String) status.getOrDefault("status", "Stopped"));
        log.setDate(LocalDate.now().toString());
        log.setTime(LocalTime.now().withNano(0).toString()); // cleaner time format
        log.setWaterUsed(random.nextInt(90) + 10); // random 10–100 liters
        log.setElectricityUsed(random.nextInt(5) + 1); // random 1–5 kWh

        neelluRepository.save(log);

        response.put("message", "✅ Neellu log saved successfully!");
        return response;
    }

    // ✅ Get irrigation logs for current logged-in user
    @GetMapping("/neelluLog")
    public List<NeelluLog> getNeelluLog(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return List.of(); // return empty if not logged in
        return neelluRepository.findAllByUsernameOrderByDateDescTimeDesc(username);
    }

    // ✅ New endpoint for frontend history page to fetch username
    @GetMapping("/api/currentUser")
    public String getCurrentUser(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        return (username != null) ? username : "";
    }
}
