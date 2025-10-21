package com.agrix.agrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrix.agrix.model.Settings;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepository;

    @PostMapping("/save")
    public Settings saveSettings(@RequestBody Settings settings) {
        return settingsRepository.save(settings);
    }

    @GetMapping("/all")
    public Iterable<Settings> getAllSettings() {
        return settingsRepository.findAll();
    }
}
