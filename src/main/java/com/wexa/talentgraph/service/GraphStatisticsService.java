package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.AdminStatsDto;
import com.wexa.talentgraph.repository.GraphStatisticsRepository;
import org.springframework.stereotype.Service;

/**
 * Service for graph statistics operations.
 */
@Service
public class GraphStatisticsService {

    private final GraphStatisticsRepository graphStatisticsRepository;

    public GraphStatisticsService(GraphStatisticsRepository graphStatisticsRepository) {
        this.graphStatisticsRepository = graphStatisticsRepository;
    }

    /**
     * Get global graph statistics for network explorer.
     * Delegates to GraphStatisticsRepository.
     *
     * @return AdminStatsDto containing total nodes, relationships, density, and type breakdowns
     */
    public AdminStatsDto getGlobalStatistics() {
        return graphStatisticsRepository.getGlobalStatistics();
    }
}