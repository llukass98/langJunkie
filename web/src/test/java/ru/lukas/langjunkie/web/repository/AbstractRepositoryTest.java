package ru.lukas.langjunkie.web.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Dmitry Lukashevich
 */
@DataJpaTest
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@Sql(value = {"classpath:schema.sql"})
@ActiveProfiles("test")
public abstract class AbstractRepositoryTest {

    @Autowired
    SessionFactory sessionFactory;
}
