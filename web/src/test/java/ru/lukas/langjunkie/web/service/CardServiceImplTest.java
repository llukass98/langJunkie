package ru.lukas.langjunkie.web.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.exception.CardNotFoundException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
@DisplayName("CardService tests")
public class CardServiceImplTest {

    @Autowired
    private CardService cardService;

    private final CardDto card = CardDto.builder()
            .backSide("backside")
            .frontSide("frontside")
            .language(DictionaryCollection.FAEN)
            .word("word")
            .build();

    @Nested
    @DisplayName("saveCard() tests")
    public class SaveCardTests {

        @Test
        @DisplayName("throws UsernameNotFoundException")
        public void shouldThrowUsernameNotFoundException() {
            assertThrows(UsernameNotFoundException.class,
                    () -> cardService.saveCard(card, "non-existent_username"));
        }

        @Test
        @DisplayName("throws no exception if user exists")
        public void shouldThrowNoExceptionIfUserExists() throws IOException {
            cardService.saveCard(card, "admin");
        }
    }

    @Nested
    @DisplayName("deleteCard() tests")
    public class DeleteCardTests {

        @Test
        @DisplayName("throws CardNotFoundException")
        public void shouldThrowCardNotFoundException() {
            assertThrows(CardNotFoundException.class,
                    () -> cardService.deleteCard(999L));
        }

        @Test
        @DisplayName("throws no exception if card exists")
        public void shouldThrowNoExceptionIfCardExists() throws IOException {
            cardService.deleteCard(1L);
        }
    }

    @Nested
    @DisplayName("updateCard() tests")
    public class UpdateCardTests {

        private final CardDto card = CardDto.builder().id(999L).build();

        @Test
        @DisplayName("throws CardNotFoundException when wrong ID")
        public void shouldThrowCardNotFoundException() {
            assertThrows(CardNotFoundException.class,
                    () -> cardService.updateCard(card));
        }

        @Test
        @DisplayName("throws no exception if card exists")
        public void shouldThrowNoExceptionIfCardExists() throws IOException {
            card.setId(1L);

            cardService.updateCard(card);
        }
    }
}
