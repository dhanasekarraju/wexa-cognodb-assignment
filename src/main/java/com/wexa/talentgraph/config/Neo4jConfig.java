package com.wexa.talentgraph.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Neo4j/CognoDB connection.
 * Uses environment variables for connection details as per requirements.
 */
@Configuration
public class Neo4jConfig {

    @Value("${COGNODB_URI}")
    private String uri;

    @Value("${COGNODB_USERNAME}")
    private String username;

    @Value("${COGNODB_PASSWORD}")
    private String password;

    /**
     * Creates and configures the Neo4j Driver bean.
     * The driver is configured to connect to CognoDB using Bolt protocol.
     *
     * @return configured Neo4j Driver instance
     */
    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}