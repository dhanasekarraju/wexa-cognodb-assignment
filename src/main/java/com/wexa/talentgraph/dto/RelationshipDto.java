package com.wexa.talentgraph.dto;

import java.util.Map;

/**
 * Data Transfer Object for a relationship in the graph visualization.
 */
public class RelationshipDto {

    private String id;
    private String type;
    private String startNodeId;
    private String endNodeId;
    private Map<String, Object> properties;

    // Constructors
    public RelationshipDto() {}

    public RelationshipDto(String id, String type, String startNodeId, String endNodeId, Map<String, Object> properties) {
        this.id = id;
        this.type = type;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
        this.properties = properties;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStartNodeId() { return startNodeId; }
    public void setStartNodeId(String startNodeId) { this.startNodeId = startNodeId; }
    public String getEndNodeId() { return endNodeId; }
    public void setEndNodeId(String endNodeId) { this.endNodeId = endNodeId; }
    public Map<String, Object> getProperties() { return properties; }
    public void setProperties(Map<String, Object> properties) { this.properties = properties; }
}