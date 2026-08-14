package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.service.SeedDataService;
import org.springframework.http.ResponseEntity;
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

    public AdminController(SeedDataService seedDataService) {
        this.seedDataService = seedDataService;
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
}