package com.agrix.agrix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrix.agrix.model.NeelluLog;

public interface NeelluRepository extends JpaRepository<NeelluLog, Long> {
    List<NeelluLog> findAllByUsernameOrderByDateDescTimeDesc(String username);
}
