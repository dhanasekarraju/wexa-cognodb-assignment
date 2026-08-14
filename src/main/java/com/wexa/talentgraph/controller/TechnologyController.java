package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.TechnologyDto;
import com.wexa.talentgraph.service.TechnologyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Technology entities.
 */
@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    /**
     * Get all technologies with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<TechnologyDto>> getAllTechnologies(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<TechnologyDto> technologies = technologyService.findAll(limit);
        return ResponseEntity.ok(technologies);
    }

    /**
     * Get technology by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDto> getTechnologyById(@PathVariable("id") Long id) {
        TechnologyDto technology = technologyService.findById(id);
        if (technology != null) {
            return ResponseEntity.ok(technology);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a technology.
     */
    @PostMapping
    public ResponseEntity<TechnologyDto> createTechnology(@RequestBody TechnologyDto technologyDto) {
        TechnologyDto savedTechnology = technologyService.save(technologyDto);
        return ResponseEntity.ok(savedTechnology);
    }
}