package ru.lukas.langjunkie.web.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.dto.UserDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dmitry Lukashevich
 */
@SpringBootTest
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@Sql(value = {"classpath:schema.sql"})
@ActiveProfiles("dev")
@DisplayName("UserService tests")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("loadUserByUsername() tests")
    public class getUserByUsernameTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        public void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> userService.loadUserByUsername("non-existent_username"));
        }

        @Test
        @DisplayName("returns correct UserDetails")
        public void shouldReturnCorrectUserDetails() {
            UserDetails userDetails = userService.loadUserByUsername("admin");

            assertThat(userDetails.getUsername(), is("admin"));
        }
    }

    @Nested
    @DisplayName("getUserByUsernameAsDto() tests")
    public class getUserByUsernameAsDtoTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        public void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> userService.getUserByUsernameAsDto("non-existent_username"));
        }

        @Test
        @DisplayName("returns correct userDto")
        public void shouldReturnCorrectUserDto() {
            UserDto userDto = userService.getUserByUsernameAsDto("admin");

            assertThat(userDto.getUsername(), is("admin"));
            assertThat(userDto.getFullname(), is("John Dough"));
            assertThat(userDto.getCards().size(), is(1));
            assertThat(userDto.getEmail(), is("admin@admin.org"));
        }
    }

    @Nested
    @DisplayName("saveUser() tests")
    public class saveUserTests {

        private final CreateUserDto user = CreateUserDto.builder()
                .username("username")
                .email("email@mail.org")
                .fullname("fullname")
                .password("useruser")
                .build();

        @Test
        @DisplayName("throws ConstrainViolationException when username exists")
        public void shouldThrowConstraintViolationExceptionIfUsernameExists() {
            user.setUsername("admin");

            assertThrows(DataIntegrityViolationException.class,
                    () -> userService.saveUser(user));
        }

        @Test
        @DisplayName("throws ConstrainViolationException when email exists")
        public void shouldThrowConstraintViolationExceptionIfEmailExists() {
            user.setEmail("admin@admin.org");

            assertThrows(DataIntegrityViolationException.class,
                    () -> userService.saveUser(user));
        }

        @Test
        @DisplayName("throws no exception if all required data is present")
        public void shouldThrowNoExceptionIfUserExists() {
            userService.saveUser(user);
        }
    }
}
