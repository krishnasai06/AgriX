package com.agrix.agrix.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElectricityController {

    @GetMapping("/currentElectricity")
    public Map<String, Object> getElectricityUsage() {
        Map<String, Object> response = new HashMap<>();
        Random random = new Random();

        double usage = 0.5 + (5.0 - 0.5) * random.nextDouble(); // between 0.5–5 kWh
        response.put("usage", String.format("%.2f", usage));

        return response;
    }
}
