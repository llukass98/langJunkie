package ru.lukas.langjunkie.web.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.dto.UserViewDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dmitry Lukashevich
 */
@DisplayName("UserService tests")
public class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("loadUserByUsername() tests")
    class getUserByUsernameTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> userService.loadUserByUsername("non-existent_username"));
        }

        @Test
        @DisplayName("returns correct UserDetails")
        void shouldReturnCorrectUserDetails() {
            UserDetails userDetails = userService.loadUserByUsername("admin");

            assertThat(userDetails.getUsername(), is("admin"));
        }
    }

    @Nested
    @DisplayName("saveUser() tests")
    class saveUserTests {

        private final CreateUserDto user = CreateUserDto.builder()
                .username("username")
                .email("email@mail.org")
                .fullname("fullname")
                .password("useruser")
                .build();

        @Test
        @DisplayName("throws ConstrainViolationException when username exists")
        void shouldThrowConstraintViolationExceptionIfUsernameExists() {
            user.setUsername("admin");

            assertThrows(DataIntegrityViolationException.class,
                    () -> userService.saveUser(user));
        }

        @Test
        @DisplayName("throws ConstrainViolationException when email exists")
        void shouldThrowConstraintViolationExceptionIfEmailExists() {
            user.setEmail("admin@admin.org");

            assertThrows(DataIntegrityViolationException.class,
                    () -> userService.saveUser(user));
        }

        @Test
        @DisplayName("throws no exception if all required data is present")
        void shouldThrowNoExceptionIfUserExists() {
            assertDoesNotThrow(() -> userService.saveUser(user), "should not throw any exception");
        }
    }

    @Nested
    @DisplayName("getUserViewByUsername() tests")
    class getUserViewByUsernameTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> userService.getUserViewByUsername("non-existent_username"));
        }

        @Test
        @DisplayName("returns correct UserViewDto")
        void shouldReturnCorrectUserViewDto() {
            UserViewDto userDto = userService.getUserViewByUsername("admin");

            assertThat(userDto.getId(), is(1L));
            assertThat(userDto.getUsername(), is("admin"));
            assertThat(userDto.getFullName(), is("John Dough"));
        }
    }
}
