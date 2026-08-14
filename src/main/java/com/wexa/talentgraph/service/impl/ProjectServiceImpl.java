package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.ProjectDto;
import com.wexa.talentgraph.repository.ProjectRepository;
import com.wexa.talentgraph.service.ProjectService;
import com.wexa.talentgraph.dto.TalentRecommendationDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of ProjectService.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDto> findAll(Integer limit) {
        return projectRepository.findAll(limit);
    }

    @Override
    public ProjectDto findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public ProjectDto save(ProjectDto projectDto) {
        return projectRepository.save(projectDto);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    /**
     * Get talent recommendations for a project.
     */
    public List<TalentRecommendationDto> getProjectRecommendations(Long projectId, Integer limit) {
        return projectRepository.getProjectRecommendations(projectId, limit);
    }
}