package com.wexa.talentgraph.controller;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Health check endpoint to verify the application and database are running.
 */
@RestController
public class HealthController {

    @Autowired
    private Driver driver;

    /**
     * Health check endpoint that returns application and database status.
     *
     * @return map containing status information
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> status = new HashMap<>();
        status.put("service", "TalentGraph");
        try (Session session = driver.session()) {
            // Run a simple query to verify the connection
            session.run("RETURN 1");
            status.put("status", "UP");
            status.put("database", "UP");
        } catch (Neo4jException e) {
            status.put("status", "DEGRADED");
            status.put("database", "DOWN");
            // Optionally log the error
            e.printStackTrace();
        }
        return status;
    }
}