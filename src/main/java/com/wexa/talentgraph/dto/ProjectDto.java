package com.wexa.talentgraph.dto;

/**
 * Data Transfer Object for Project entity.
 */
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private String status;

    // Constructors
    public ProjectDto() {}

    public ProjectDto(Long id, String name, String description, String status) {
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