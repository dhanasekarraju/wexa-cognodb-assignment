package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.SkillDto;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Repository for Skill nodes using Neo4j Java Driver directly.
 */
@Repository
public class SkillRepository extends BaseRepository {

    public SkillRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all skills with optional limit.
     */
    public List<SkillDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (s:Skill) RETURN s";
            if (limit != null && limit > 0) {
                query += " LIMIT " + limit;
            }

            Result result = session.run(query);
            List<SkillDto> skills = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("s").asNode();
                SkillDto skill = new SkillDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("category").asString()
                );
                skills.add(skill);
            }

            return skills;
        }
    }

    /**
     * Find skill by ID.
     */
    public SkillDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (s:Skill {id: $id}) RETURN s";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("s").asNode();

                Long skillId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String category = node.get("category").isNull() ? null : node.get("category").asString();

                return new SkillDto(skillId, name, category);
            }

            return null;
        }
    }

    /**
     * Save a skill node.
     */
    public SkillDto save(SkillDto skillDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (s:Skill {id: $id})
                SET s.name = $name,
                    s.category = $category
                RETURN s
                """;

            Result result = session.run(query, Map.of(
                "id", skillDto.getId() != null ? skillDto.getId() : -1,
                "name", skillDto.getName(),
                "category", skillDto.getCategory()
            ));

            Record record = result.single();
            Node node = record.get("s").asNode();

            Long skillId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String category = node.get("category").isNull() ? null : node.get("category").asString();

            return new SkillDto(skillId, name, category);
        }
    }
}