package com.wexa.talentgraph.model;

/**
 * Company node representing a company where people work.
 * This is a plain POJO used for mapping records from Neo4j queries.
 */
public class Company {

    private Long id;
    private String name;
    private String industry;

    // Constructors
    public Company() {}

    public Company(Long id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
}