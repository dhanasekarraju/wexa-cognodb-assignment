package com.wexa.talentgraph.repository;

import com.wexa.talentgraph.dto.PersonDto;
import com.wexa.talentgraph.dto.PersonSimilarityDto;
import com.wexa.talentgraph.dto.PersonNetworkDto;
import com.wexa.talentgraph.dto.NodeDto;
import com.wexa.talentgraph.dto.RelationshipDto;
import com.wexa.talentgraph.model.Person;
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
 * Repository for Person nodes using Neo4j Java Driver directly.
 */
@Repository
public class PersonRepository extends BaseRepository {

    public PersonRepository(Driver driver) {
        super(driver);
    }

    /**
     * Find all persons with optional limit.
     */
    public List<PersonDto> findAll(Integer limit) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person) RETURN p";
            // Validate limit parameter to prevent excessive resource consumption
            if (limit != null && limit > 0) {
                // Cap the limit at a reasonable maximum to prevent overload
                int safeLimit = Math.min(limit, 1000);
                query += " LIMIT " + safeLimit;
            }

            Result result = session.run(query);
            List<PersonDto> persons = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("p").asNode();
                PersonDto person = new PersonDto(
                    node.get("id").asLong(),
                    node.get("name").asString(),
                    node.get("title").asString(),
                    node.get("experienceYears").asInt(),
                    node.get("location").asString(),
                    node.get("summary").asString()
                );
                persons.add(person);
            }

            return persons;
        }
    }

    /**
     * Find person by ID.
     */
    public PersonDto findById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person {id: $id}) RETURN p";
            Result result = session.run(query, Map.of("id", id));

            if (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("p").asNode();

                Long personId = node.get("id").isNull() ? null : node.get("id").asLong();
                String name = node.get("name").isNull() ? null : node.get("name").asString();
                String title = node.get("title").isNull() ? null : node.get("title").asString();
                Integer experienceYears = node.get("experienceYears").isNull() ? null : node.get("experienceYears").asInt();
                String location = node.get("location").isNull() ? null : node.get("location").asString();
                String summary = node.get("summary").isNull() ? null : node.get("summary").asString();

                return new PersonDto(personId, name, title, experienceYears, location, summary);
            }

            return null;
        }
    }

    /**
     * Save a person node.
     */
    public PersonDto save(PersonDto personDto) {
        try (Session session = driver.session()) {
            String query = """
                MERGE (p:Person {id: $id})
                SET p.name = $name,
                    p.title = $title,
                    p.experienceYears = $experienceYears,
                    p.location = $location,
                    p.summary = $summary
                RETURN p
                """;

            Result result = session.run(query, Map.of(
                "id", personDto.getId() != null ? personDto.getId() : -1,
                "name", personDto.getName(),
                "title", personDto.getTitle(),
                "experienceYears", personDto.getExperienceYears(),
                "location", personDto.getLocation(),
                "summary", personDto.getSummary()
            ));

            Record record = result.single();
            Node node = record.get("p").asNode();

            Long personId = node.get("id").isNull() ? null : node.get("id").asLong();
            String name = node.get("name").isNull() ? null : node.get("name").asString();
            String title = node.get("title").isNull() ? null : node.get("title").asString();
            Integer experienceYears = node.get("experienceYears").isNull() ? null : node.get("experienceYears").asInt();
            String location = node.get("location").isNull() ? null : node.get("location").asString();
            String summary = node.get("summary").isNull() ? null : node.get("summary").asString();

            return new PersonDto(personId, name, title, experienceYears, location, summary);
        }
    }

    /**
     * Find similar persons based on skills, technologies, domains, and project experience.
     * This is a multi-hop traversal query that demonstrates graph advantages for similarity matching.
     */
    @SuppressWarnings("unchecked")
    public List<PersonSimilarityDto> findSimilarPersons(Long personId, Integer limit) {
        try (Session session = driver.session()) {
            String query = """
                MATCH (person:Person {id: $personId})
                // Get person's skills, technologies, domains, and companies
                OPTIONAL MATCH (person)-[hs:HAS_SKILL]->(s:Skill)
                OPTIONAL MATCH (s)-[rt:RELATED_TO]->(t:Technology)
                OPTIONAL MATCH (person)-[wo:WORKED_ON]->(p:Project)-[in:IN_DOMAIN]->(d:Domain)
                OPTIONAL MATCH (person)-[wa:WORKS_AT]->(c:Company)
                WITH person,
                     COLLECT(DISTINCT s.name) AS personSkills,
                     COLLECT(DISTINCT t.name) AS personTechnologies,
                     COLLECT(DISTINCT d.name) AS personDomains,
                     COLLECT(DISTINCT c.name) AS personCompanies
                // Find other people with overlapping attributes
                MATCH (other:Person)
                WHERE other.id <> person.id
                OPTIONAL MATCH (other)-[hs2:HAS_SKILL]->(s2:Skill)
                OPTIONAL MATCH (s2)-[rt2:RELATED_TO]->(t2:Technology)
                OPTIONAL MATCH (other)-[wo2:WORKED_ON]->(p2:Project)-[in2:IN_DOMAIN]->(d2:Domain)
                OPTIONAL MATCH (other)-[wa2:WORKS_AT]->(c2:Company)
                WITH person,
                     other,
                     personSkills,
                     personTechnologies,
                     personDomains,
                     personCompanies,
                     COLLECT(DISTINCT s2.name) AS otherSkills,
                     COLLECT(DISTINCT t2.name) AS otherTechnologies,
                     COLLECT(DISTINCT d2.name) AS otherDomains,
                     COLLECT(DISTINCT c2.name) AS otherCompanies
                // Calculate similarity score and prepare reasons
                RETURN other {
                    .id, .name, .title, .experienceYears, .location, .summary
                } AS person,
                (
                    (size([skill IN personSkills WHERE skill IN otherSkills]) * 10) +
                    (size([tech IN personTechnologies WHERE tech IN otherTechnologies]) * 5) +
                    (size([dom IN personDomains WHERE dom IN otherDomains]) * 8) +
                    (size([comp IN personCompanies WHERE comp IN otherCompanies]) * 3)
                ) AS similarityScore,
                [
                    CASE WHEN size([skill IN personSkills WHERE skill IN otherSkills]) > 0
                         THEN size([skill IN personSkills WHERE skill IN otherSkills]) + " shared skills"
                         ELSE "No shared skills"
                    END,
                    CASE WHEN size([tech IN personTechnologies WHERE tech IN otherTechnologies]) > 0
                         THEN size([tech IN personTechnologies WHERE tech IN otherTechnologies]) + " shared technologies"
                         ELSE "No shared technologies"
                    END,
                    CASE WHEN size([dom IN personDomains WHERE dom IN otherDomains]) > 0
                         THEN size([dom IN personDomains WHERE dom IN otherDomains]) + " shared domains"
                         ELSE "No shared domains"
                    END,
                    CASE WHEN size([comp IN personCompanies WHERE comp IN otherCompanies]) > 0
                         THEN size([comp IN personCompanies WHERE comp IN otherCompanies]) + " shared companies"
                         ELSE "No shared companies"
                    END
                ] AS reasons
                ORDER BY similarityScore DESC
                LIMIT $limit
                """;

            Result result = session.run(query, Map.of(
                "personId", personId,
                "limit", limit != null ? limit : 10
            ));

            List<PersonSimilarityDto> similarities = new ArrayList<>();

            while (result.hasNext()) {
                Record record = result.next();
                Value personValue = record.get("person");
                @SuppressWarnings("unchecked")
                Map<String, Object> personMap = personValue.asMap();

                Long foundPersonId = personMap.get("id") == null ? null : ((Number) personMap.get("id")).longValue();
                String name = personMap.get("name") == null ? null : (String) personMap.get("name");
                String title = personMap.get("title") == null ? null : (String) personMap.get("title");
                Integer experienceYears = personMap.get("experienceYears") == null ? null : ((Number) personMap.get("experienceYears")).intValue();
                String location = personMap.get("location") == null ? null : (String) personMap.get("location");
                String summary = personMap.get("summary") == null ? null : (String) personMap.get("summary");

                PersonDto person = new PersonDto(foundPersonId, name, title, experienceYears, location, summary);

                List<Object> reasonsObjectList = record.get("reasons") == null ? new ArrayList<>() : record.get("reasons").asList();
                List<String> reasonsList = new ArrayList<>();
                for (Object reason : reasonsObjectList) {
                    reasonsList.add(reason == null ? null : reason.toString());
                }

                PersonSimilarityDto similarity = new PersonSimilarityDto(
                    person,
                    record.get("similarityScore") == null ? 0 : record.get("similarityScore").asInt(),
                    reasonsList
                );

                similarities.add(similarity);
            }

            return similarities;
        }
    }

    /**
     * Get the network neighborhood for a person.
     * Returns nodes and edges suitable for graph visualization.
     */
    public PersonNetworkDto getPersonNetwork(Long personId) {
        try (Session session = driver.session()) {
            String query = """
                MATCH (p:Person {id: $personId})
                OPTIONAL MATCH (p)-[r1:HAS_SKILL]->(s:Skill)
                OPTIONAL MATCH (p)-[r2:WORKED_ON]->(pr:Project)
                OPTIONAL MATCH (p)-[r3:WORKS_AT]->(c:Company)
                OPTIONAL MATCH (pr)-[r4:IN_DOMAIN]->(d:Domain)
                OPTIONAL MATCH (s)-[r5:RELATED_TO]->(t:Technology)
                WITH p,
                     COLLECT(DISTINCT s) AS skills,
                     COLLECT(DISTINCT pr) AS projects,
                     COLLECT(DISTINCT c) AS companies,
                     COLLECT(DISTINCT d) AS domains,
                     COLLECT(DISTINCT t) AS technologies,
                     COLLECT(DISTINCT r1) AS hasSkillRels,
                     COLLECT(DISTINCT r2) AS workedOnRels,
                     COLLECT(DISTINCT r3) AS worksAtRels,
                     COLLECT(DISTINCT r4) AS inDomainRels,
                     COLLECT(DISTINCT r5) AS relatedToRels
                // Create a list of all nodes: person, skills, projects, companies, domains, technologies
                WITH [p] + skills + projects + companies + domains + technologies AS allNodes,
                     hasSkillRels + workedOnRels + worksAtRels + inDomainRels + relatedToRels AS allRels
                UNWIND allNodes AS node
                WITH
                     COLLECT({
                         id: id(node) + '',
                         label: labels(node)[0],
                         properties: properties(node)
                     }) AS nodes,
                     allRels
                UNWIND allRels AS rel
                WITH nodes,
                     COLLECT({
                         id: id(rel) + '',
                         type: type(rel),
                         startNodeId: id(startNode(rel)) + '',
                         endNodeId: id(endNode(rel)) + '',
                         properties: properties(rel)
                     }) AS relationships
                RETURN nodes, relationships
                """;

            Result result = session.run(query, Map.of("personId", personId));
            if (result.hasNext()) {
                Record record = result.next();
                List<Object> nodesList = record.get("nodes") == null ? new ArrayList<>() : record.get("nodes").asList();
                List<Object> relsList = record.get("relationships") == null ? new ArrayList<>() : record.get("relationships").asList();

                List<NodeDto> nodes = new ArrayList<>();
                for (Object nodeObj : nodesList) {
                    Map<String, Object> nodeMap = (Map<String, Object>) nodeObj;
                    NodeDto nodeDto = new NodeDto(
                            nodeMap.get("id") == null ? null : (String) nodeMap.get("id"),
                            nodeMap.get("label") == null ? null : (String) nodeMap.get("label"),
                            nodeMap.get("properties") == null ? new HashMap<>() : (Map<String, Object>) nodeMap.get("properties")
                    );
                    nodes.add(nodeDto);
                }

                List<RelationshipDto> relationships = new ArrayList<>();
                for (Object relObj : relsList) {
                    Map<String, Object> relMap = (Map<String, Object>) relObj;
                    RelationshipDto relDto = new RelationshipDto(
                            relMap.get("id") == null ? null : (String) relMap.get("id"),
                            relMap.get("type") == null ? null : (String) relMap.get("type"),
                            relMap.get("startNodeId") == null ? null : (String) relMap.get("startNodeId"),
                            relMap.get("endNodeId") == null ? null : (String) relMap.get("endNodeId"),
                            relMap.get("properties") == null ? new HashMap<>() : (Map<String, Object>) relMap.get("properties")
                    );
                    relationships.add(relDto);
                }

                return new PersonNetworkDto(nodes, relationships);
            }

            return new PersonNetworkDto(new ArrayList<>(), new ArrayList<>());
        }
    }

    /**
     * Delete a person by ID.
     */
    public void deleteById(Long id) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person {id: $id}) DETACH DELETE p";
            session.run(query, Map.of("id", id));
        }
    }
}