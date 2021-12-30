package ru.lukas.langjunkie.web.repository;

import org.hibernate.stat.Statistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import ru.lukas.langjunkie.web.model.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dmitry Lukashevich
 */
@DisplayName("UserRepository tests")
class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("findByUsername() tests")
    class FindByUsernameTests {

        @Test
        @DisplayName("returns an empty Optional if user has not been found")
        void shouldReturnAnEmptyOptionalIfUserDoesntExist() {
            Optional<User> user = userRepository.findByUsername("non-existent");

            assertThat(user.isEmpty(), is(true));
        }

        @Test
        @DisplayName("returns correct User if user with such username is present in DB")
        void shouldReturnUserIfUserIsPresentInDb() {
            Optional<User> user = userRepository.findByUsername("admin");
            User expectedUser = User.builder().username("admin").build();

            assertThat(user.isPresent(), is(true));
            assertThat(user.get(), is(expectedUser));
        }

        @Test
        @DisplayName("executes only one query when retrieves User from DB")
        void shouldExecuteOnlyOneQueryWhenRetrievesUser() {
            Statistics stats = sessionFactory.getStatistics();

            Long before = stats.getQueryExecutionCount();
            userRepository.findByUsername("admin");
            Long after = stats.getQueryExecutionCount();

            assertThat(after - before, is(1L));
        }

        @Test
        @DisplayName("eagerly loads all Cards with User from DB")
        void shouldEagerlyLoadAllCardsAlongWithUser() {
            Statistics stats = sessionFactory.getStatistics();

            Long entitiesBefore = stats.getEntityLoadCount();
            userRepository.findByUsername("admin");
            Long entitiesAfter = stats.getEntityLoadCount();

            assertThat(entitiesAfter - entitiesBefore, is(9L));
        }
    }
}
