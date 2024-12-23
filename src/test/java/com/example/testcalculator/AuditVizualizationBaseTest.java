package com.example.testcalculator;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(initializers = {AuditVizualizationBaseTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, SqlScriptsTestExecutionListener.class, FlywayTestExecutionListener.class})
public abstract class AuditVizualizationBaseTest {
    protected static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.3-alpine");
        POSTGRE_SQL_CONTAINER.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "database.cache.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "database.cache.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
                    "database.cache.password=" + POSTGRE_SQL_CONTAINER.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
