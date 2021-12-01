package ru.lukas.langjunkie.web.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dbCredentials")
    @Profile({"prod"})
    public Map<String, String> dbCredentialsProd() throws URISyntaxException  {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        return Map.of(
                "username", dbUri.getUserInfo().split(":")[0],
                "password", dbUri.getUserInfo().split(":")[1],
                "dbUrl", "jdbc:postgresql://" + dbUri.getHost() + ':'
                        + dbUri.getPort() + dbUri.getPath() + "?sslmode=require"
        );
    }

    @Bean(name = "dbCredentials")
    @Profile({"dev"})
    public Map<String, String> dbCredentialsDev() {
        return Map.of(
                "username", "postgres",
                "password", "root",
                "dbUrl", "jdbc:postgresql://localhost:5432/langjunkie"
        );
    }

    @Bean
    public DataSource dataSource(Map<String, String> dbCredentials) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(dbCredentials.get("dbUrl"));
        dataSourceBuilder.username(dbCredentials.get("username"));
        dataSourceBuilder.password(dbCredentials.get("password"));

        return dataSourceBuilder.build();
    }
}