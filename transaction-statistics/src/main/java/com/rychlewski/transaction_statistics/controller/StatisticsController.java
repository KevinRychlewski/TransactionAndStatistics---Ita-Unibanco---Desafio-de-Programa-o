package com.rychlewski.transaction_statistics.controller;

import com.rychlewski.transaction_statistics.dto.StatisticsResponseDTO;
import com.rychlewski.transaction_statistics.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        StatisticsResponseDTO response = statisticsService.getStatistics();
        return ResponseEntity.ok(response);
    }

}
