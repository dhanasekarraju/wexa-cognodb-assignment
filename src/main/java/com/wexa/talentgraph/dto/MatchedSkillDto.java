package com.wexa.talentgraph.dto;

/**
 * Data Transfer Object for matched skills with proficiency and years.
 */
public class MatchedSkillDto {

    private String skill;
    private String proficiency;
    private Integer years;

    // Constructors
    public MatchedSkillDto() {}

    public MatchedSkillDto(String skill, String proficiency, Integer years) {
        this.skill = skill;
        this.proficiency = proficiency;
        this.years = years;
    }

    // Getters and Setters
    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }
    public String getProficiency() { return proficiency; }
    public void setProficiency(String proficiency) { this.proficiency = proficiency; }
    public Integer getYears() { return years; }
    public void setYears(Integer years) { this.years = years; }
}