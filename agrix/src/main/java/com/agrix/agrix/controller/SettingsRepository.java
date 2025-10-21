package com.agrix.agrix.controller;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrix.agrix.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Optional<Settings> findByUsername(String username);
}
