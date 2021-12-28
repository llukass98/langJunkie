package ru.lukas.langjunkie.web.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.dto.CreateCardDto;
import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.exception.CardNotFoundException;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
@SpringBootTest
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@Sql(value = {"classpath:schema.sql"})
@ActiveProfiles("dev")
@DisplayName("CardService tests")
public class CardServiceImplTest {

    @Autowired
    private CardService cardService;

    private final CreateCardDto card = CreateCardDto.builder()
            .backSide("backside")
            .frontSide("frontside")
            .language(DictionaryCollection.FAEN)
            .word("word")
            .build();

    @Nested
    @DisplayName("saveCard() tests")
    class SaveCardTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> cardService.saveCard(card, "non-existent_username"));
        }

        @Test
        @DisplayName("throws no exception if user exists")
        void shouldThrowNoExceptionIfUserExists() throws IOException {
            assertDoesNotThrow(() -> cardService.saveCard(card, "admin"),
                    "should not throw any exception");
        }
    }

    @Nested
    @DisplayName("deleteCard() tests")
    class DeleteCardTests {

        @Test
        @DisplayName("throws CardNotFoundException")
        void shouldThrowCardNotFoundException() {
            assertThrows(CardNotFoundException.class,
                    () -> cardService.deleteCard(999L));
        }

        @Test
        @DisplayName("throws no exception if card exists")
        void shouldThrowNoExceptionIfCardExists() throws IOException {
            assertDoesNotThrow(() -> cardService.deleteCard(1L),
                    "should not throw any exception");
        }
    }

    @Nested
    @DisplayName("updateCard() tests")
    class UpdateCardTests {

        private final CreateCardDto card = CreateCardDto.builder().id(999L).build();

        @Test
        @DisplayName("throws CardNotFoundException when wrong ID")
        void shouldThrowCardNotFoundException() {
            assertThrows(CardNotFoundException.class,
                    () -> cardService.updateCard(card));
        }

        @Test
        @DisplayName("throws no exception if card exists")
        void shouldThrowNoExceptionIfCardExists() throws IOException {
            card.setId(1L);

            assertDoesNotThrow(() -> cardService.updateCard(card),
                    "should not throw any exception");
        }
    }

    @Nested
    @DisplayName("getAllCardViewByUserId() tests")
    class getAllCardViewByUserIdTests {

        private final Pageable pageable = PageRequest.of(0, 20);

        @Test
        @DisplayName(
                "returns Page<CardViewDto> with all cards if specified user has cards" +
                "and pageSize is gt number of cards")
        void shouldReturnPageOfCardViewDtoIfUserHasCards() {
            Page<CardViewDto> cardViewDtos = cardService.getAllCardViewByUserId(1L, pageable);

            assertThat(cardViewDtos.getTotalElements(), is(8L));
            assertThat(cardViewDtos.getTotalPages(), is(1));
            assertThat(cardViewDtos.getContent().size(), is(8));
        }

        @Test
        @DisplayName(
                "returns Page<CardViewDto> with specified number of cards per page " +
                "if specified user has cards")
        void shouldReturnSpecifiedNumberOfCardViewDtoIfUserHasCards() {
            Page<CardViewDto> cardViewDtos =
                    cardService.getAllCardViewByUserId(1L, PageRequest.of(0, 4));

            assertThat(cardViewDtos.getContent().size(), is(4));
            assertThat(cardViewDtos.getTotalPages(), is(2));
        }

        @Test
        @DisplayName("CardViewDto is mapped correctly")
        void shouldCorrectlyMapCardViewDto() {
            Page<CardViewDto> cardViewDtos = cardService.getAllCardViewByUserId(1L, pageable);
            CardViewDto card = cardViewDtos.stream().findFirst().get();

            assertThat(card.getId(), is(1L));
            assertThat(card.getWord().getValue(), is("capable"));
            assertThat(card.getLanguage(), is(DictionaryCollection.FAEN));
            assertThat(card.getFrontSide(), is("This is what I'm capable of"));
            assertThat(card.getBackSide(), is("Back side"));

            //assertNull(card.getImage(), "image should be null");
        }

        @Test
        @DisplayName("returns an empty Page if no user found")
        void shouldReturnAnEmptyPageIfNoUserFound() {
            Page<CardViewDto> cardViewDtos = cardService.getAllCardViewByUserId(999L, pageable);

            assertThat(cardViewDtos.getTotalElements(), is(0L));
        }

        @Test
        @DisplayName("returns an empty Page if user has no cards")
        void shouldReturnAnEmptyPageIfUserHasNoCards() {
            Page<CardViewDto> cardViewDtos = cardService.getAllCardViewByUserId(2L, pageable);

            assertThat(cardViewDtos.getTotalElements(), is(0L));
        }
    }
}
