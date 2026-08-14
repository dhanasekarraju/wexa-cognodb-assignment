package com.wexa.talentgraph.model;

/**
 * Technology node representing a technology that skills can be related to.
 * This is a plain POJO used for mapping records from Neo4j queries.
 */
public class Technology {

    private Long id;
    private String name;
    private String category;

    // Constructors
    public Technology() {}

    public Technology(Long id, String name, String category) {
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