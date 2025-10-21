package com.agrix.agrix.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterController {

    @GetMapping("/currentUsage")
    public Map<String, Object> getWaterUsage() {
        Map<String, Object> response = new HashMap<>();
        Random random = new Random();

        double usage = 10 + (100 - 10) * random.nextDouble(); // between 10–100 liters
        response.put("usage", String.format("%.2f", usage));

        return response;
    }
}
