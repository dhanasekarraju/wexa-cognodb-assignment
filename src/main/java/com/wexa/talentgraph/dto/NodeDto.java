package com.wexa.talentgraph.dto;

import java.util.Map;

/**
 * Data Transfer Object for a node in the graph visualization.
 */
public class NodeDto {

    private String id;
    private String label;
    private Map<String, Object> properties;

    // Constructors
    public NodeDto() {}

    public NodeDto(String id, String label, Map<String, Object> properties) {
        this.id = id;
        this.label = label;
        this.properties = properties;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public Map<String, Object> getProperties() { return properties; }
    public void setProperties(Map<String, Object> properties) { this.properties = properties; }
}