package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.AdminStatsDto;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for graph statistics operations.
 */
@Repository
public class GraphStatisticsRepository extends BaseRepository {

    public GraphStatisticsRepository(Driver driver) {
        super(driver);
    }

    /**
     * Get global graph statistics for network explorer.
     * Returns total nodes, relationships, density, and type breakdowns.
     */
    public AdminStatsDto getGlobalStatistics() {
        try (Session session = getDriver().session()) {
            // Query to get counts by node label
            String nodeQuery = """
                MATCH (n)
                RETURN labels(n)[0] as label, count(n) as count
                """;

            // Query to get counts by relationship type
            String relQuery = """
                MATCH ()-[r]->()
                RETURN type(r) as type, count(r) as count
                """;

            // Query to get total counts for density calculation
            String totalQuery = """
                MATCH (n)
                WITH count(n) as nodeCount
                MATCH ()-[r]->()
                WITH nodeCount, count(r) as relCount
                RETURN nodeCount, relCount
                """;

            Result nodeResult = session.run(nodeQuery);
            Result relResult = session.run(relQuery);
            Result totalResult = session.run(totalQuery);

            // Process node type counts
            List<AdminStatsDto.TypeCount> nodeTypes = new ArrayList<>();
            long totalNodes = 0;

            while (nodeResult.hasNext()) {
                Record record = nodeResult.next();
                String label = record.get("label").asString();
                long count = record.get("count").asLong();
                totalNodes += count;
                nodeTypes.add(new AdminStatsDto.TypeCount(label, count));
            }

            // Process relationship type counts
            List<AdminStatsDto.TypeCount> relationshipTypes = new ArrayList<>();
            long totalRelationships = 0;

            while (relResult.hasNext()) {
                Record record = relResult.next();
                String type = record.get("type").asString();
                long count = record.get("count").asLong();
                totalRelationships += count;
                relationshipTypes.add(new AdminStatsDto.TypeCount(type, count));
            }

            // Calculate network density
            double density = 0.0;
            if (totalResult.hasNext()) {
                Record record = totalResult.next();
                long nodeCount = record.get("nodeCount").asLong();
                long relCount = record.get("relCount").asLong();

                if (nodeCount > 1) {
                    // Density = actual relationships / possible relationships
                    // For directed graph: possible = n*(n-1)
                    // For undirected graph: possible = n*(n-1)/2
                    // We'll use undirected formula as approximation
                    double possible = (double) nodeCount * (nodeCount - 1) / 2;
                    density = possible > 0 ? (double) relCount / possible : 0.0;
                }
            }

            return new AdminStatsDto(
                    totalNodes,
                    totalRelationships,
                    density,
                    nodeTypes,
                    relationshipTypes
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Let the controller handle the exception
        }
    }
}