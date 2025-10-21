package com.agrix.agrix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NeelluLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // link log to user
    private String date;
    private String time;
    private String city;
    private int moisture;
    private String status;
    private int waterUsed;
    private int electricityUsed;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getMoisture() { return moisture; }
    public void setMoisture(int moisture) { this.moisture = moisture; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getWaterUsed() { return waterUsed; }
    public void setWaterUsed(int waterUsed) { this.waterUsed = waterUsed; }

    public int getElectricityUsed() { return electricityUsed; }
    public void setElectricityUsed(int electricityUsed) { this.electricityUsed = electricityUsed; }
}
