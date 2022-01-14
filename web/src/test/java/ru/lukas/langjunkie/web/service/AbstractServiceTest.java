package ru.lukas.langjunkie.web.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Dmitry Lukashevich
 */
@SpringBootTest
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@Sql(value = {"classpath:schema.sql"})
@ActiveProfiles("test")
public abstract class AbstractServiceTest {
}
