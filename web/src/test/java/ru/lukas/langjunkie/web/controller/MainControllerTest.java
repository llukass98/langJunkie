package ru.lukas.langjunkie.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.dto.UserViewDto;
import ru.lukas.langjunkie.web.model.Word;
import ru.lukas.langjunkie.web.service.CardService;
import ru.lukas.langjunkie.web.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Dmitry Lukashevich
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
@WithMockUser
@DisplayName("MainController tests")
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CardService cardService;

    private final UserViewDto userView = new UserViewDto() {

        @Override
        public Long getId() { return 1L; }

        @Override
        public String getUsername() { return "admin"; }

        @Override
        public String getFullName() { return "Administrator"; }
    };

    private final CardViewDto cardView = new CardViewDto() {

        @Override
        public Long getId() { return 1L; }

        @Override
        public String getFrontSide() { return "front side"; }

        @Override
        public String getBackSide() { return "back side"; }

        @Override
        public DictionaryCollection getLanguage() { return DictionaryCollection.FAEN; }

        @Override
        public Word getWord() { return new Word("word"); }
    };

    private final Page<CardViewDto> page = new PageImpl<>(List.of(cardView));

    @BeforeEach
    public void setUp() {
        when(userService.getUserViewByUsername(any())).thenReturn(userView);
        when(cardService.getAllCardViewByUserId(anyLong(), isA(PageRequest.class)))
                .thenReturn(page);
    }

    @Test
    @DisplayName("successfully returns index page")
    void shouldReturnIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("collections"))
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("successfully returns dictionary page")
    void shouldReturnDictionaryPage() throws Exception {
        mockMvc.perform(get("/dictionary"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("collections"))
                .andExpect(view().name("dictionary"));
    }

    @Test
    @DisplayName("successfully returns cards page")
    void shouldReturnCardsPage() throws Exception {
        mockMvc.perform(get("/cards"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("collections"))
                .andExpect(model().attributeExists("cards"))
                .andExpect(view().name("cards"));
    }
}
