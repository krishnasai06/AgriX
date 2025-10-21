package com.agrix.agrix.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrix.agrix.model.Settings;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/soil")
@CrossOrigin(origins = "http://localhost:8080") // Adjust to your frontend origin
public class SoilController {

    @Autowired
    private SettingsRepository settingsRepository;

    private Map<String, Object> currentSettings = new HashMap<>();
    private int currentMoisture = new Random().nextInt(101);
    private boolean irrigating = false;
    private final Random random = new Random();

    // ✅ Save settings for the logged-in user
    @PostMapping("/saveSettings")
    public Map<String, String> saveSettings(@RequestParam String city,
                                            @RequestParam String soilType,
                                            @RequestParam int threshold,
                                            HttpSession session) {

        Map<String, String> response = new HashMap<>();
        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            response.put("message", "⚠️ User not logged in!");
            return response;
        }

        // Check if user already has settings
        Settings settings = settingsRepository.findByUsername(username)
                .orElse(new Settings());

        settings.setUsername(username);
        settings.setCity(city);
        settings.setSoilType(soilType);
        settings.setThreshold(threshold);

        settingsRepository.save(settings);

        // Update current settings map
        currentSettings.put("username", username);
        currentSettings.put("city", city);
        currentSettings.put("soilType", soilType);
        currentSettings.put("threshold", threshold);

        response.put("message", "✅ Settings saved successfully for " + username);
        return response;
    }
    // Add this in SoilController
public Map<String, Object> getCurrentSoilStatusForUser(String username) {
    Map<String, Object> status = new HashMap<>();

    Optional<Settings> userSettings = settingsRepository.findByUsername(username);
    int threshold = userSettings.map(Settings::getThreshold).orElse(40);

    // Simulate moisture changes
    if (currentMoisture < threshold) {
        irrigating = true;
        currentMoisture += 2;
        if (currentMoisture > threshold) currentMoisture = threshold;
    } else {
        irrigating = false;
        currentMoisture -= 1;
        if (currentMoisture < 0) currentMoisture = 0;
    }

    status.put("username", username);
    status.put("city", userSettings.map(Settings::getCity).orElse("N/A"));
    status.put("value", currentMoisture);
    status.put("status", irrigating ? "Irrigating" : "Stopped");
    return status;
}


    // ✅ Get saved settings for logged-in user
    @GetMapping("/settings")
    public Map<String, Object> getSettings(HttpSession session) {
        Map<String, Object> userSettings = new HashMap<>();
        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            userSettings.put("message", "⚠️ User not logged in!");
            return userSettings;
        }

        settingsRepository.findByUsername(username).ifPresent(settings -> {
            userSettings.put("city", settings.getCity());
            userSettings.put("soilType", settings.getSoilType());
            userSettings.put("threshold", settings.getThreshold());
        });

        return userSettings;
    }

    // ✅ Simulate moisture updates per user's threshold
    @GetMapping("/moisture")
    public Map<String, Object> getMoisture(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            response.put("message", "⚠️ User not logged in!");
            return response;
        }

        Optional<Settings> userSettings = settingsRepository.findByUsername(username);
        int threshold = userSettings.map(Settings::getThreshold).orElse(40);

        // Simulate moisture changes
        if (currentMoisture < threshold) {
            irrigating = true;
            currentMoisture += 2;
            if (currentMoisture > threshold) currentMoisture = threshold;
        } else {
            irrigating = false;
            currentMoisture -= 1;
            if (currentMoisture < 0) currentMoisture = 0;
        }

        response.put("username", username);
        response.put("city", userSettings.map(Settings::getCity).orElse("N/A"));
        response.put("value", currentMoisture);
        response.put("status", irrigating ? "Irrigating" : "Stopped");

        return response;
    }

    // ✅ Optional: admin/debug — view all settings
    @GetMapping("/all")
    public Iterable<Settings> getAllSettings() {
        return settingsRepository.findAll();
    }
}
