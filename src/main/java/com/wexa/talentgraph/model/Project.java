package com.wexa.talentgraph.model;

/**
 * Project node representing a project in the talent graph.
 * This is a plain POJO used for mapping records from Neo4j queries.
 */
public class Project {

    private Long id;
    private String name;
    private String description;
    private String status;

    // Constructors
    public Project() {}

    public Project(Long id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}