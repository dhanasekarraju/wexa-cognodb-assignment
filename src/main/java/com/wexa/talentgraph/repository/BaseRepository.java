package com.wexa.talentgraph.repository;

import org.neo4j.driver.Driver;
import org.springframework.stereotype.Repository;

/**
 * Base repository providing common Neo4j driver access.
 */
@Repository
public class BaseRepository {

    protected final Driver driver;

    public BaseRepository(Driver driver) {
        this.driver = driver;
    }

    /**
     * Get the Neo4j driver.
     *
     * @return the Neo4j driver
     */
    public Driver getDriver() {
        return driver;
    }
}