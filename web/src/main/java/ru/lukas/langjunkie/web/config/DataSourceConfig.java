package ru.lukas.langjunkie.web.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DB_URL = "dbUrl";

    @Bean(name = "dbCredentials")
    @Profile({"prod"})
    public Map<String, String> dbCredentialsProd() throws URISyntaxException  {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        return Map.of(
                USERNAME, dbUri.getUserInfo().split(":")[0],
                PASSWORD, dbUri.getUserInfo().split(":")[1],
                DB_URL, "jdbc:postgresql://" + dbUri.getHost() + ':'
                        + dbUri.getPort() + dbUri.getPath() + "?sslmode=require"
        );
    }

    @Bean(name = "dbCredentials")
    @Profile({"dev"})
    public Map<String, String> dbCredentialsDev() {
        return Map.of(
                USERNAME, "postgres",
                PASSWORD, "root",
                DB_URL, "jdbc:postgresql://localhost:5432/langjunkie"
        );
    }

    @Bean
    public DataSource dataSource(Map<String, String> dbCredentials) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(dbCredentials.get(DB_URL));
        dataSourceBuilder.username(dbCredentials.get(USERNAME));
        dataSourceBuilder.password(dbCredentials.get(PASSWORD));

        return dataSourceBuilder.build();
    }
}
