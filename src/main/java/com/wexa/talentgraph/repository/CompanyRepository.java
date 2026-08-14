package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.CompanyDto;
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
 * Repository for Company nodes using Neo4j Java Driver directly.
 */
@Repository
public class CompanyRepository extends BaseRepository {

    public CompanyRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all companies with optional limit.
     */
    public List<CompanyDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (c:Company) RETURN c";
            if (limit != null && limit > 0) {
                query += " LIMIT " + limit;
            }

            Result result = session.run(query);
            List<CompanyDto> companies = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("c").asNode();
                CompanyDto company = new CompanyDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("industry").asString()
                );
                companies.add(company);
            }

            return companies;
        }
    }

    /**
     * Find company by ID.
     */
    public CompanyDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (c:Company {id: $id}) RETURN c";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("c").asNode();

                Long companyId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String industry = node.get("industry").isNull() ? null : node.get("industry").asString();

                return new CompanyDto(companyId, name, industry);
            }

            return null;
        }
    }

    /**
     * Save a company node.
     */
    public CompanyDto save(CompanyDto companyDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (c:Company {id: $id})
                SET c.name = $name,
                    c.industry = $industry
                RETURN c
                """;

            Result result = session.run(query, Map.of(
                "id", companyDto.getId() != null ? companyDto.getId() : -1,
                "name", companyDto.getName(),
                "industry", companyDto.getIndustry()
            ));

            Record record = result.single();
            Node node = record.get("c").asNode();

            Long companyId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String industry = node.get("industry").isNull() ? null : node.get("industry").asString();

            return new CompanyDto(companyId, name, industry);
        }
    }
}