package ru.lukas.langjunkie.web.repository;

import org.hibernate.stat.Statistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.ImageFileInfo;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.model.Word;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dmitry Lukashevich
 */
@DisplayName("CardRepository tests")
class CardRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Nested
    @DisplayName("hasImage() tests")
    class HasImageTests {

        @Test
        @DisplayName("returns true if card has image")
        void shouldReturnTrueIfCardHasImage() {
            boolean hasImage = cardRepository.hasImage(2L);

            assertThat(hasImage, is(true));
        }

        @Test
        @DisplayName("returns false if no image is present in card")
        void shouldReturnFalseIfNoImageIsPresentInCard() {
            boolean hasImage = cardRepository.hasImage(1L);

            assertThat(hasImage, is(false));
        }

        @Test
        @DisplayName("executes only one query")
        void shouldExecuteOnlyOneQuery() {
            Statistics stats = sessionFactory.getStatistics();

            Long before = stats.getQueryExecutionCount();
            cardRepository.hasImage(2L);
            Long after = stats.getQueryExecutionCount();

            assertThat(after - before, is(1L));
        }
    }

    @Nested
    @DisplayName("findByIdEagerFetch() tests")
    class FindByIdEagerFetchTests {

        @Test
        @DisplayName("returns an empty Optional if card doesn't exist")
        void shouldReturnAnEmptyOptionalIfCardDoesntExist() {
            Optional<Card> card = cardRepository.findByIdEagerFetch(9999L);

            assertThat(card.isEmpty(), is(true));
        }

        @Test
        @DisplayName("returns Card with User and ImageFileInfo loaded when card Id is valid")
        void shouldReturnCardWithUserAndImageFileInfoLoaded() {
            Optional<Card> card = cardRepository.findByIdEagerFetch(2L);

            assertThat(card.isPresent(), is(true));
            assertThat(card.get().getUser(), is(User.builder().username("admin").build()));

            assertThat(card.get().getImage(), is(ImageFileInfo.builder()
                    .filename("1234-6456-23423-425gdf-gfewr-245.jpg")
                    .size(209785L).build()));

            assertThat(card.get(), is(Card.builder()
                    .frontSide("This is what I'm capable of")
                    .backSide("Back side")
                    .word(new Word("capable"))
                    .language(DictionaryCollection.FAEN).build()));
        }

        @Test
        @DisplayName("executes only one query when eagerly fetches User and ImageFileInfo")
        void shouldExecuteOnlyOneQueryWhenEagerlyFetchesUserAndImageFileInfo() {
            Statistics stats = sessionFactory.getStatistics();

            Long before = stats.getQueryExecutionCount();
            cardRepository.findByIdEagerFetch(2L);
            Long after = stats.getQueryExecutionCount();

            assertThat(after - before, is(1L));
        }
    }
}
