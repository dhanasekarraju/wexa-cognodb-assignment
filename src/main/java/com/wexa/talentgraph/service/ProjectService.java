package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.ProjectDto;
import com.wexa.talentgraph.dto.TalentRecommendationDto;
import java.util.List;

/**
 * Service for Project operations.
 */
public interface ProjectService {

    List<ProjectDto> findAll(Integer limit);

    ProjectDto findById(Long id);

    ProjectDto save(ProjectDto projectDto);

    void deleteById(Long id);

    List<TalentRecommendationDto> getProjectRecommendations(Long projectId, Integer limit);
}