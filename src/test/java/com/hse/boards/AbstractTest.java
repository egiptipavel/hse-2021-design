package com.hse.boards;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTest {
    protected static final MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("test")
            .withUsername("hse")
            .withPassword("password")
            .withReuse(true);

    @BeforeAll
    public static void beforeAll() {
        container.start();
    }
}
