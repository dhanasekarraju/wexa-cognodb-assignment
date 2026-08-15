package com.wexa.talentgraph.dto;

import java.util.List;

/**
 * Data Transfer Object for global graph statistics.
 */
public class AdminStatsDto {

    private long totalNodes;
    private long totalRelationships;
    private double networkDensity;
    private List<TypeCount> nodeTypes;
    private List<TypeCount> relationshipTypes;

    // Constructors
    public AdminStatsDto() {}

    public AdminStatsDto(long totalNodes, long totalRelationships, double networkDensity,
                         List<TypeCount> nodeTypes, List<TypeCount> relationshipTypes) {
        this.totalNodes = totalNodes;
        this.totalRelationships = totalRelationships;
        this.networkDensity = networkDensity;
        this.nodeTypes = nodeTypes;
        this.relationshipTypes = relationshipTypes;
    }

    // Getters and Setters
    public long getTotalNodes() { return totalNodes; }
    public void setTotalNodes(long totalNodes) { this.totalNodes = totalNodes; }

    public long getTotalRelationships() { return totalRelationships; }
    public void setTotalRelationships(long totalRelationships) { this.totalRelationships = totalRelationships; }

    public double getNetworkDensity() { return networkDensity; }
    public void setNetworkDensity(double networkDensity) { this.networkDensity = networkDensity; }

    public List<TypeCount> getNodeTypes() { return nodeTypes; }
    public void setNodeTypes(List<TypeCount> nodeTypes) { this.nodeTypes = nodeTypes; }

    public List<TypeCount> getRelationshipTypes() { return relationshipTypes; }
    public void setRelationshipTypes(List<TypeCount> relationshipTypes) { this.relationshipTypes = relationshipTypes; }

    /**
     * Simple DTO for type-count pairs.
     */
    public static class TypeCount {
        private String type;
        private long count;

        // Constructors
        public TypeCount() {}

        public TypeCount(String type, long count) {
            this.type = type;
            this.count = count;
        }

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
}