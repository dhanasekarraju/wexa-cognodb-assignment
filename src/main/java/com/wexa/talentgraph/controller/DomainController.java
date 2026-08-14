package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.DomainDto;
import com.wexa.talentgraph.service.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Domain entities.
 */
@RestController
@RequestMapping("/api/domains")
public class DomainController {

    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * Get all domains with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<DomainDto>> getAllDomains(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<DomainDto> domains = domainService.findAll(limit);
        return ResponseEntity.ok(domains);
    }

    /**
     * Get domain by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DomainDto> getDomainById(@PathVariable("id") Long id) {
        DomainDto domain = domainService.findById(id);
        if (domain != null) {
            return ResponseEntity.ok(domain);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a domain.
     */
    @PostMapping
    public ResponseEntity<DomainDto> createDomain(@RequestBody DomainDto domainDto) {
        DomainDto savedDomain = domainService.save(domainDto);
        return ResponseEntity.ok(savedDomain);
    }
}