package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.SkillDto;
import com.wexa.talentgraph.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Skill entities.
 */
@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Get all skills with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<SkillDto>> getAllSkills(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<SkillDto> skills = skillService.findAll(limit);
        return ResponseEntity.ok(skills);
    }

    /**
     * Get skill by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id) {
        SkillDto skill = skillService.findById(id);
        if (skill != null) {
            return ResponseEntity.ok(skill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a skill.
     */
    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
        SkillDto savedSkill = skillService.save(skillDto);
        return ResponseEntity.ok(savedSkill);
    }
}