package com.wexa.talentgraph.dto;

import java.util.List;

/**
 * Data Transfer Object for person similarity results.
 */
public class PersonSimilarityDto {

    private PersonDto person;
    private Integer similarityScore;
    private List<String> reasons;

    // Constructors
    public PersonSimilarityDto() {}

    public PersonSimilarityDto(PersonDto person, Integer similarityScore, List<String> reasons) {
        this.person = person;
        this.similarityScore = similarityScore;
        this.reasons = reasons;
    }

    // Getters and Setters
    public PersonDto getPerson() { return person; }
    public void setPerson(PersonDto person) { this.person = person; }
    public Integer getSimilarityScore() { return similarityScore; }
    public void setSimilarityScore(Integer similarityScore) { this.similarityScore = similarityScore; }
    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}