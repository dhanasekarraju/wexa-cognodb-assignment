package com.wexa.talentgraph.model;

/**
 * Person node representing an individual in the talent graph.
 * This is a plain POJO used for mapping records from Neo4j queries.
 */
public class Person {

    private Long id;
    private String name;
    private String title;
    private Integer experienceYears;
    private String location;
    private String summary;

    // Constructors
    public Person() {}

    public Person(Long id, String name, String title, Integer experienceYears, String location, String summary) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.experienceYears = experienceYears;
        this.location = location;
        this.summary = summary;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}