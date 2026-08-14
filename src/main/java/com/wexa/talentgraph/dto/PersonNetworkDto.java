package com.wexa.talentgraph.dto;

import java.util.List;

/**
 * Data Transfer Object for person network visualization.
 */
public class PersonNetworkDto {

    private List<NodeDto> nodes;
    private List<RelationshipDto> relationships;

    // Constructors
    public PersonNetworkDto() {}

    public PersonNetworkDto(List<NodeDto> nodes, List<RelationshipDto> relationships) {
        this.nodes = nodes;
        this.relationships = relationships;
    }

    // Getters and Setters
    public List<NodeDto> getNodes() { return nodes; }
    public void setNodes(List<NodeDto> nodes) { this.nodes = nodes; }
    public List<RelationshipDto> getRelationships() { return relationships; }
    public void setRelationships(List<RelationshipDto> relationships) { this.relationships = relationships; }
}