package com.agrix.agrix.controller; // or .service

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agrix.agrix.model.NeelluLog;

@Component
@EnableScheduling
public class AutoNeelluLogger {

    @Autowired
    private NeelluRepository neelluRepository;

    @Autowired
    private SoilController soilController;

    @Autowired
    private UserSessionService userSessionService;

    private final Random random = new Random();

    // Run every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void logAllUsers() {
        for (String username : userSessionService.getAllLoggedInUsers()) {
            Map<String, Object> status = soilController.getCurrentSoilStatusForUser(username);

            NeelluLog log = new NeelluLog();
            log.setUsername(username);
            log.setCity((String) status.getOrDefault("city", "N/A"));
            log.setMoisture((Integer) status.getOrDefault("value", 0));
            log.setStatus((String) status.getOrDefault("status", "Stopped"));
            log.setDate(LocalDate.now().toString());
            log.setTime(LocalTime.now().toString());
            log.setWaterUsed((Integer) status.getOrDefault("value", 0));
            log.setElectricityUsed(random.nextInt(5) + 1);

            neelluRepository.save(log);
        }
    }
}
