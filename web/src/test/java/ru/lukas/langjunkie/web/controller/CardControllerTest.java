package ru.lukas.langjunkie.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ru.lukas.langjunkie.web.service.CardService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Dmitry Lukashevich
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("dev")
@DisplayName("CardController tests")
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Nested
    @DisplayName("POST /card/add tests")
    class PostCardAddTests {

        @Test
        @DisplayName("successfully redirects to /cards")
        void postAddShouldRedirectToCards() throws Exception {
            mockMvc.perform(post("/card/add")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("word=word&frontSide=f&backSide=b&language=FAEN&username=u"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().attributeDoesNotExist("errors"))
                    .andExpect(model().errorCount(0))
                    .andExpect(view().name("redirect:/cards"));
        }

        @Test
        @DisplayName("returns 400 BAD_REQUEST when username is missing")
        void postAddReturnsBadRequestWhenUsernameIsMissing() throws Exception {
            mockMvc.perform(post("/card/add")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content("word=word&frontSide=f&backSide=b&language=FAEN"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    @DisplayName("POST /card/update successfully redirects to /cards")
    void postUpdateShouldRedirectToCards() throws Exception {
        mockMvc.perform(post("/card/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=1&word=word&frontSide=f&backSide=b&language=FAEN&username=u"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/cards"));
    }

    @Test
    @DisplayName("POST /card/delete/{card-id} successfully redirects to /cards")
    void deleteShouldRedirectToCards() throws Exception {
        mockMvc.perform(delete("/card/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/cards"));
    }
}
