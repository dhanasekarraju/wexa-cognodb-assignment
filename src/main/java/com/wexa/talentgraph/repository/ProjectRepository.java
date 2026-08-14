package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for Project nodes using Neo4j Java Driver directly.
 */
@Repository
public class ProjectRepository extends BaseRepository {

    public ProjectRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all projects with optional limit.
     */
    public List<ProjectDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Project) RETURN p";
            if (limit != null && limit > 0) {
                query += " LIMIT " + limit;
            }

            Result result = session.run(query);
            List<ProjectDto> projects = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("p").asNode();
                ProjectDto project = new ProjectDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("description").asString(),
                    node.get("status").asString()
                );
                projects.add(project);
            }

            return projects;
        }
    }

    /**
     * Find project by ID.
     */
    public ProjectDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Project {id: $id}) RETURN p";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("p").asNode();

                Long projectId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String description = node.get("description").isNull() ? null : node.get("description").asString();
                String status = node.get("status").isNull() ? null : node.get("status").asString();

                return new ProjectDto(projectId, name, description, status);
            }

            return null;
        }
    }

    /**
     * Get project recommendations based on skills and domain experience.
     * This is a multi-hop traversal query that demonstrates graph advantages.
     */
    public List<TalentRecommendationDto> getProjectRecommendations(Long projectId, Integer limit) {
        try (Session session = driver.session()) {
            String query = """
                MATCH (p:Project {id: $projectId})
                OPTIONAL MATCH (p)-[in2:IN_DOMAIN]->(personDomain:Domain)
                MATCH (p)-[req:REQUIRES_SKILL]->(s:Skill)
                MATCH (person:Person)-[hs:HAS_SKILL]->(s)
                OPTIONAL MATCH (person)-[wo:WORKED_ON]->(proj2:Project)-[in:IN_DOMAIN]->(d:Domain)
                WHERE personDomain.name = d.name
                WITH person,
                     COLLECT(DISTINCT {
                         skill: s.name,
                         proficiency: hs.proficiency,
                         years: hs.years
                     }) AS matchedSkills,
                     COUNT(DISTINCT s) AS matchedSkillCount,
                     CASE WHEN COUNT(DISTINCT d) > 0 THEN true ELSE false END AS domainExperience
                MATCH (person)-[hs2:HAS_SKILL]->(s2:Skill)
                MATCH (s2)-[rt:RELATED_TO]->(t:Technology)
                WHERE t.name IN [
                    "Java", "Spring Boot", "React", "TypeScript",
                    "Kafka", "PostgreSQL", "Redis", "Docker",
                    "Kubernetes", "AWS", "Python"
                ]
                WITH person,
                     matchedSkills,
                     matchedSkillCount,
                     domainExperience,
                     COLLECT(DISTINCT t.name) AS relatedTechnologies,
                     // Calculate score based on matched skills, proficiency, etc.
                     (matchedSkillCount * 10) +
                     (CASE WHEN domainExperience THEN 20 ELSE 0 END) +
                     (reduce(sum = 0, sk IN matchedSkills |
                         sum +
                         CASE sk.proficiency
                             WHEN "EXPERT" THEN 15
                             WHEN "ADVANCED" THEN 10
                             WHEN "INTERMEDIATE" THEN 5
                             WHEN "BEGINNER" THEN 2
                             ELSE 0 END)) AS score
                RETURN person {
                    .id, .name, .title, .experienceYears, .location, .summary
                } AS person,
                score,
                matchedSkills,
                domainExperience,
                relatedTechnologies,
                [
                    CASE WHEN matchedSkillCount >= 3 THEN "3+ required skills matched"
                         WHEN matchedSkillCount >= 2 THEN "2 required skills matched"
                         WHEN matchedSkillCount >= 1 THEN "1 required skill matched"
                         ELSE "No required skills matched"
                    END,
                    CASE WHEN domainExperience THEN "Previous experience in same domain"
                         ELSE "No domain experience"
                    END,
                    CASE WHEN size(relatedTechnologies) > 0 THEN "Experience with related technologies"
                         ELSE "No related technology experience"
                    END
                ] AS reasons
                ORDER BY score DESC
                LIMIT $limit
                """;

            Result result = session.run(query, Map.of(
                "projectId", projectId,
                "limit", limit != null ? limit : 10
            ));

            List<TalentRecommendationDto> recommendations = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Value personValue = record.get("person");
                Map<String, Object> personMap = personValue.asMap();

                Long personId = personMap.get("id") == null ? null : ((Number) personMap.get("id")).longValue();
                String name = personMap.get("name") == null ? null : (String) personMap.get("name");
                String title = personMap.get("title") == null ? null : (String) personMap.get("title");
                Integer experienceYears = personMap.get("experienceYears") == null ? null : ((Number) personMap.get("experienceYears")).intValue();
                String location = personMap.get("location") == null ? null : (String) personMap.get("location");
                String summary = personMap.get("summary") == null ? null : (String) personMap.get("summary");

                PersonDto person = new PersonDto(personId, name, title, experienceYears, location, summary);

                List<Object> matchedSkillsList = record.get("matchedSkills") == null ? new ArrayList<>() : record.get("matchedSkills").asList();
                List<MatchedSkillDto> matchedSkills = new ArrayList<>();
                for (Object skillObj : matchedSkillsList) {
                    Map<String, Object> skillMap = (Map<String, Object>) skillObj;
                    matchedSkills.add(new MatchedSkillDto(
                        skillMap.get("skill") == null ? null : (String) skillMap.get("skill"),
                        skillMap.get("proficiency") == null ? null : (String) skillMap.get("proficiency"),
                        skillMap.get("years") == null ? null : ((Number) skillMap.get("years")).intValue()
                    ));
                }

                List<Object> relatedTechList = record.get("relatedTechnologies") == null ? new ArrayList<>() : record.get("relatedTechnologies").asList();
                List<String> relatedTechnologies = new ArrayList<>();
                for (Object tech : relatedTechList) {
                    relatedTechnologies.add(tech == null ? null : (String) tech);
                }

                List<Object> reasonsList = record.get("reasons") == null ? new ArrayList<>() : record.get("reasons").asList();
                List<String> reasons = new ArrayList<>();
                for (Object reason : reasonsList) {
                    reasons.add(reason == null ? null : (String) reason);
                }

                Boolean domainExperience = record.get("domainExperience") == null ? false : record.get("domainExperience").asBoolean();

                TalentRecommendationDto recommendation = new TalentRecommendationDto(
                    person,
                    record.get("score") == null ? 0 : record.get("score").asInt(),
                    matchedSkills,
                    domainExperience,
                    relatedTechnologies,
                    reasons
                );

                recommendations.add(recommendation);
            }

            return recommendations;
        }
    }

    /**
     * Save a project node.
     */
    public ProjectDto save(ProjectDto projectDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (p:Project {id: $id})
                SET p.name = $name,
                    p.description = $description,
                    p.status = $status
                RETURN p
                """;

            Result result = session.run(query, Map.of(
                "id", projectDto.getId() != null ? projectDto.getId() : -1,
                "name", projectDto.getName(),
                "description", projectDto.getDescription(),
                "status", projectDto.getStatus()
            ));

            Record record = result.single();
            Node node = record.get("p").asNode();

            Long projectId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String description = node.get("description").isNull() ? null : node.get("description").asString();
            String status = node.get("status").isNull() ? null : node.get("status").asString();

            return new ProjectDto(projectId, name, description, status);
        }
    }

    /**
     * Delete a project by ID.
     */
    public void deleteById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Project {id: $id}) DETACH DELETE p";
            session.run(query, Map.of("id", id));
        }
    }
}