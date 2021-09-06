package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

/**
 * Configuration for connection pool
 * and dataSource
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */

@Configuration
@Profile("prod")
@ComponentScan("com.epam.esm")
@PropertySource("classpath:property/db.properties")
public class RepositoryConfiguration {

    @Value("${db.driver}")
    private String driverName;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariDataSource hikariConfig = new HikariDataSource();
        hikariConfig.setDriverClassName(driverName);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return hikariConfig;
    }

    @Bean
    public TransactionManager transactionManager(HikariDataSource hikariDataSource) {
        return new DataSourceTransactionManager(hikariDataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

}
