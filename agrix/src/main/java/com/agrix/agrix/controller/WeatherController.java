package com.agrix.agrix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    private final String API_KEY = "7657f90e85c11f43fe4dbbb6497d2947"; // replace with your API key
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/weather")
    public Map<String, Object> getWeather(@RequestParam String city) {
        Map<String, Object> result = new HashMap<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = BASE_URL + "?q=" + city + "&units=metric&appid=" + API_KEY;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null) {
                Map<String, Object> main = (Map<String, Object>) response.get("main");
                Map<String, Object> weather = ((java.util.List<Map<String, Object>>) response.get("weather")).get(0);

                result.put("temperature", main.get("temp"));
                result.put("condition", weather.get("description"));
                result.put("humidity", main.get("humidity"));
            }
        } catch (Exception e) {
            result.put("error", "Failed to fetch weather for " + city);
        }

        return result;
    }
}
