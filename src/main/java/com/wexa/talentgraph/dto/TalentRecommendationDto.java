package com.wexa.talentgraph.dto;

import java.util.List;

/**
 * Data Transfer Object for talent recommendations.
 */
public class TalentRecommendationDto {

    private PersonDto person;
    private Integer score;
    private List<MatchedSkillDto> matchedSkills;
    private Boolean domainExperience;
    private List<String> relatedTechnologies;
    private List<String> reasons;

    // Constructors
    public TalentRecommendationDto() {}

    public TalentRecommendationDto(PersonDto person, Integer score, List<MatchedSkillDto> matchedSkills,
                                   Boolean domainExperience, List<String> relatedTechnologies, List<String> reasons) {
        this.person = person;
        this.score = score;
        this.matchedSkills = matchedSkills;
        this.domainExperience = domainExperience;
        this.relatedTechnologies = relatedTechnologies;
        this.reasons = reasons;
    }

    // Getters and Setters
    public PersonDto getPerson() { return person; }
    public void setPerson(PersonDto person) { this.person = person; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<MatchedSkillDto> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<MatchedSkillDto> matchedSkills) { this.matchedSkills = matchedSkills; }
    public Boolean getDomainExperience() { return domainExperience; }
    public void setDomainExperience(Boolean domainExperience) { this.domainExperience = domainExperience; }
    public List<String> getRelatedTechnologies() { return relatedTechnologies; }
    public void setRelatedTechnologies(List<String> relatedTechnologies) { this.relatedTechnologies = relatedTechnologies; }
    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}