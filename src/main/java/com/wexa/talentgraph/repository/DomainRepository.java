package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.DomainDto;
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
 * Repository for Domain nodes using Neo4j Java Driver directly.
 */
@Repository
public class DomainRepository extends BaseRepository {

    public DomainRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all domains with optional limit.
     */
    public List<DomainDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (d:Domain) RETURN d";
            if (limit != null && limit > 0) {
                query += " LIMIT " + limit;
            }

            Result result = session.run(query);
            List<DomainDto> domains = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("d").asNode();
                DomainDto domain = new DomainDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("description").asString()
                );
                domains.add(domain);
            }

            return domains;
        }
    }

    /**
     * Find domain by ID.
     */
    public DomainDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (d:Domain {id: $id}) RETURN d";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("d").asNode();

                Long domainId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String description = node.get("description").isNull() ? null : node.get("description").asString();

                return new DomainDto(domainId, name, description);
            }

            return null;
        }
    }

    /**
     * Save a domain node.
     */
    public DomainDto save(DomainDto domainDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (d:Domain {id: $id})
                SET d.name = $name,
                    d.description = $description
                RETURN d
                """;

            Result result = session.run(query, Map.of(
                "id", domainDto.getId() != null ? domainDto.getId() : -1,
                "name", domainDto.getName(),
                "description", domainDto.getDescription()
            ));

            Record record = result.single();
            Node node = record.get("d").asNode();

            Long domainId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String description = node.get("description").isNull() ? null : node.get("description").asString();

            return new DomainDto(domainId, name, description);
        }
    }
}