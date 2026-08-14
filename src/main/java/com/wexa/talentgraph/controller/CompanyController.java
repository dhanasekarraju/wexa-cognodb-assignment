package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.CompanyDto;
import com.wexa.talentgraph.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Company entities.
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Get all companies with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<CompanyDto> companies = companyService.findAll(limit);
        return ResponseEntity.ok(companies);
    }

    /**
     * Get company by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("id") Long id) {
        CompanyDto company = companyService.findById(id);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a company.
     */
    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto savedCompany = companyService.save(companyDto);
        return ResponseEntity.ok(savedCompany);
    }
}