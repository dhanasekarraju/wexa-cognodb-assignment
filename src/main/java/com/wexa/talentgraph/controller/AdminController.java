package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.AdminStatsDto;
import com.wexa.talentgraph.service.SeedDataService;
import com.wexa.talentgraph.service.GraphStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for administrative operations.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final SeedDataService seedDataService;
    private final GraphStatisticsService graphStatisticsService;

    public AdminController(SeedDataService seedDataService, GraphStatisticsService graphStatisticsService) {
        this.seedDataService = seedDataService;
        this.graphStatisticsService = graphStatisticsService;
    }

    /**
     * Seed the database with initial data.
     * This endpoint is idempotent and safe to call multiple times.
     */
    @PostMapping("/seed")
    public ResponseEntity<Void> seedDatabase() {
        seedDataService.seedData();
        return ResponseEntity.ok().build();
    }

    /**
     * Get global graph statistics for network explorer.
     * Returns total nodes, relationships, density, and type breakdowns.
     */
    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDto> getGlobalStatistics() {
        AdminStatsDto stats = graphStatisticsService.getGlobalStatistics();
        return ResponseEntity.ok(stats);
    }
}