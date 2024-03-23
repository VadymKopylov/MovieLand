package com.kopylov.movieland;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@TestPropertySource(properties = {"spring.datasource.url=jdbc:tc:postgresql://localhost/test"})
public class AbstractBaseITest {

    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER =
                new PostgreSQLContainer<>("postgres:14.4")
                        .withDatabaseName("test")
                        .withUsername("test")
                        .withPassword("test");
        POSTGRES_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }
}
