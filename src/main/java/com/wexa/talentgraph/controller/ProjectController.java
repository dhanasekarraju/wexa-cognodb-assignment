package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.ProjectDto;
import com.wexa.talentgraph.dto.TalentRecommendationDto;
import com.wexa.talentgraph.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Project entities.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Get all projects with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<ProjectDto> projects = projectService.findAll(limit);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get project by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") Long id) {
        ProjectDto project = projectService.findById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a project.
     */
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto savedProject = projectService.save(projectDto);
        return ResponseEntity.ok(savedProject);
    }

    /**
     * Delete a project by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get talent recommendations for a project.
     * This demonstrates graph traversal for project-person matching.
     */
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<TalentRecommendationDto>> getProjectRecommendations(
            @PathVariable("id") Long id,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<TalentRecommendationDto> recommendations = projectService.getProjectRecommendations(id, limit);
        return ResponseEntity.ok(recommendations);
    }
}