package com.wexa.talentgraph.model;

/**
 * Skill node representing a skill that people can have.
 * This is a plain POJO used for mapping records from Neo4j queries.
 */
public class Skill {

    private Long id;
    private String name;
    private String category;

    // Constructors
    public Skill() {}

    public Skill(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}