package ru.lukas.langjunkie.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.lukas.langjunkie.web.api.model.Dictionary;

import java.util.Map;
import java.util.Properties;

/**
 * @author Dmitry Lukashevich
 */
@Configuration
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setHibernateProperties(hibernateProperties);
        sessionFactory.setAnnotatedClasses(Dictionary.class);

        return sessionFactory;
    }

    @Bean
    public Properties hibernateProperties(Map<String, String> dbCredentials) {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "5");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty(
                "hibernate.connection.driver_class", "org.postgresql.Driver");
        hibernateProperties.setProperty("hibernate.c3p0.min_size", "5");
        hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
        hibernateProperties.setProperty("hibernate.c3p0.timeout", "300");
        hibernateProperties.setProperty(
                "hibernate.c3p0.idle_test_periods", "300");
        hibernateProperties.setProperty(
                "hibernate.connection.provider_class",
                "org.hibernate.connection.C3P0ConnectionProvider");
        hibernateProperties.setProperty(
                "hibernate.connection.url", dbCredentials.get("dbUrl"));
        hibernateProperties.setProperty(
                "hibernate.connection.username", dbCredentials.get("username"));
        hibernateProperties.setProperty(
                "hibernate.connection.password", dbCredentials.get("password"));

        return hibernateProperties;
    }
}
