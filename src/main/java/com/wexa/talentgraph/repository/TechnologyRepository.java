package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.TechnologyDto;
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
 * Repository for Technology nodes using Neo4j Java Driver directly.
 */
@Repository
public class TechnologyRepository extends BaseRepository {

    public TechnologyRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all technologies with optional limit.
     */
    public List<TechnologyDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Technology) RETURN t";
            if (limit != null && limit > 0) {
                query += " LIMIT " + limit;
            }

            Result result = session.run(query);
            List<TechnologyDto> technologies = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("t").asNode();
                TechnologyDto technology = new TechnologyDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("category").asString()
                );
                technologies.add(technology);
            }

            return technologies;
        }
    }

    /**
     * Find technology by ID.
     */
    public TechnologyDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Technology {id: $id}) RETURN t";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("t").asNode();

                Long technologyId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String category = node.get("category").isNull() ? null : node.get("category").asString();

                return new TechnologyDto(technologyId, name, category);
            }

            return null;
        }
    }

    /**
     * Save a technology node.
     */
    public TechnologyDto save(TechnologyDto technologyDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (t:Technology {id: $id})
                SET t.name = $name,
                    t.category = $category
                RETURN t
                """;

            Result result = session.run(query, Map.of(
                "id", technologyDto.getId() != null ? technologyDto.getId() : -1,
                "name", technologyDto.getName(),
                "category", technologyDto.getCategory()
            ));

            Record record = result.single();
            Node node = record.get("t").asNode();

            Long technologyId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String category = node.get("category").isNull() ? null : node.get("category").asString();

            return new TechnologyDto(technologyId, name, category);
        }
    }
}