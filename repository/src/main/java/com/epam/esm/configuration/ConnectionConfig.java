package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm")
public class ConnectionConfig {
    @Bean
    public HikariDataSource hikari(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/certificate_tag?useUnicode=true&serverTimezone=UTC");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("Luki4_pups");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("springHikariCP");
        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts","true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize","250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit","2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts","true");

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(hikari());
    }

    @Bean
    public PlatformTransactionManager txManager(){
        return new DataSourceTransactionManager(Objects.requireNonNull(jdbcTemplate().getDataSource()));
    }

}
