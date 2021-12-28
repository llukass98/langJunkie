package ru.lukas.langjunkie.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

import static org.mockito.Mockito.doThrow;

/**
 * @author Dmitry Lukashevich
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@DisplayName("AuthenticationController tests")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        ConstraintViolationException emailException =
                new ConstraintViolationException("", new SQLException(), "email");
        ConstraintViolationException usernameException =
                new ConstraintViolationException("", new SQLException(), "username");

        doThrow(emailException).when(userService).saveUser(
                CreateUserDto.builder()
                        .fullname("john")
                        .username("johndough")
                        .email("email@email.com")
                        .password("password")
                        .build());

        doThrow(usernameException).when(userService).saveUser(
                CreateUserDto.builder()
                        .fullname("john")
                        .username("doughjohn")
                        .email("email@email.com")
                        .password("password")
                        .build());
    }


    @Nested
    @DisplayName("GET /signup tests")
    class GetSignUpTest {

        @Test
        @DisplayName("successfully returns signup page")
        void shouldReturnSignUpPage() throws Exception {
            mockMvc.perform(get("/signup"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("signup"));
        }
    }

    @Nested
    @DisplayName("POST /signup tests")
    class PostSignUpTest {

        @Test
        @DisplayName("returns 403 Forbidden when no csrf in post request")
        void shouldReturnForbiddenStatusCodeWhenNoCsrf() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf().useInvalidToken())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=&email=email@mail.org"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("contains no errors and redirects to / if all fields are filled correctly")
        void shouldReturnNoErrorsIfAllFieldsAreFilledCorrectly() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=John&password=password&username=john&email=email@mail.org"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("redirect:/"));
        }

        @Test
        @DisplayName("contains 4 errors if no fields are filled correctly")
        void shouldReturnFourErrorsIfNoFieldsAreFilledCorrectly() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(""))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(4))
                    .andExpect(view().name("signup"));
        }

        // Password constrains
        @Test
        @DisplayName("returns no errors and redirects to / if password length is 6")
        void shouldReturnNoErrorsAndRedirectsToIndexIfPasswordLengthIsSix()
                throws Exception
        {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=passwo&email=email@mail.org"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("redirect:/"));
        }

        @Test
        @DisplayName("returns no errors and redirects to / if password length is 15")
        void shouldReturnNoErrorsAndRedirectsToIndexIfPasswordLengthIsFifteen()
                throws Exception
        {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=passwordpasswor&email=email@mail.org"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("redirect:/"));
        }

        @Test
        @DisplayName("returns no errors and redirects to / if password length is between 6 and 15")
        void shouldReturnNoErrorsAndRedirectsToIndexIfPasswordLengthIsBetweenSixAndFifteen()
                throws Exception
        {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=passwordpa&email=email@mail.org"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("redirect:/"));
        }

        @Test
        @DisplayName("contains errors attribute in model when password is blank")
        void shouldReturnErrorsInModelIfPasswordIsBlank() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when password length is lt 6")
        void shouldReturnErrorsInModelIfPasswordLengthIsLessThanSix() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=passw&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when password length is gt 15")
        void shouldReturnErrorsInModelIfPasswordLengthIsGreaterThanFifteen() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=passwordpassword&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when password is null")
        void shouldReturnErrorsInModelIfPasswordIsNull() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }
        // End of password constrains

        // FullName constrains
        @Test
        @DisplayName("contains errors attribute in model when fullname is Blank")
        void shouldReturnErrorsInModelIfFullNameIsBlank() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=&username=john&password=passwordpa&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when fullname is null")
        void shouldReturnErrorsInModelIfFullNameIsNull() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("username=john&password=password&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }
        // End of fullName constrains

        // UserName constrains
        @Test
        @DisplayName("contains errors attribute in model when username is Blank")
        void shouldReturnErrorsInModelIfUserNameIsBlank() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=John&username=&password=passwordpa&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when username is null")
        void shouldReturnErrorsInModelIfUserNameIsNull() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&password=password&email=email@mail.org"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }
        // End of userName constrains

        // Email constrains
        @Test
        @DisplayName("contains errors attribute in model when email is Blank")
        void shouldReturnErrorsInModelIfEmailIsBlank() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=John&username=john&password=passwordpa&email="))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when email is null")
        void shouldReturnErrorsInModelIfEmailIsNull() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=john&password=password"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("contains errors attribute in model when email is not valid")
        void shouldReturnErrorsInModelIfEmailIsNotValid() throws Exception {
            mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("email=email.ru&fullname=john&username=john&password=password"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("errors"))
                    .andExpect(model().errorCount(1))
                    .andExpect(view().name("signup"));
        }
        // End of email constrains

        // Username and email unique constrains tests
        @Test
        @DisplayName(
                "returns error attribute with value \"email\" " +
                "if ConstraintViolationException email is thrown"
        )
        void shouldReturnErrorEmailInModelIfConstrainViolationIsThrown()
                throws Exception
        {
            MvcResult result = mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=johndough&password=password&email=email@email.com"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("error"))
                    .andExpect(view().name("signup"))
                    .andReturn();

            assertThat(
                    result.getModelAndView().getModelMap().getAttribute("error"),
                    is("email")
            );
        }

        @Test
        @DisplayName(
                "returns error attribute with value \"username\" " +
                "if ConstraintViolationException username is thrown"
        )
        void shouldReturnErrorUsernameInModelIfConstrainViolationIsThrown()
                throws Exception
        {
            MvcResult result = mockMvc.perform(post("/signup")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("fullname=john&username=doughjohn&password=password&email=email@email.com"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("error"))
                    .andExpect(view().name("signup"))
                    .andReturn();

            assertThat(
                    result.getModelAndView().getModelMap().getAttribute("error"),
                    is("username")
            );
        }
        // End of username and email unique constrains tests
    }

    @Nested
    @DisplayName("GET /signin tests")
    class GetSignInTest {

        @Test
        @DisplayName("successfully returns signin page")
        void shouldReturnSignInPage() throws Exception {
            mockMvc.perform(get("/signin"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("signin"));
        }
    }

    @Nested
    @DisplayName("GET /signin-failure tests")
    class GetSignInFailureTest {

        @Test
        @DisplayName("successfully returns signin page")
        void shouldReturnSignInPage() throws Exception {
            mockMvc.perform(get("/signin-failure"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("signin"));
        }
    }
}
