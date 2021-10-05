package ru.lukas.langjunkie.api;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public Map<String, String> dbCredentials() throws URISyntaxException  {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        return Map.of(
                "username", dbUri.getUserInfo().split(":")[0],
                "password", dbUri.getUserInfo().split(":")[1],
                "dbUrl", "jdbc:postgresql://" + dbUri.getHost() + ':'
                        + dbUri.getPort() + dbUri.getPath() + "?sslmode=require"
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