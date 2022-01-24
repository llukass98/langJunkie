package ru.lukas.langjunkie.dictionarycollections.enen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ru.lukas.langjunkie.dictionarycollections.AbstractDictionaryTest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;
import ru.lukas.langjunkie.dictionarycollections.enen.utils.MerriamWebsterDictionaryExpectedResults;
import ru.lukas.langjunkie.dictionarycollections.enen.utils.MerriamWebsterDictionaryJsons;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("MerriamWebster tests")
class MerriamWebsterDictionaryTest extends AbstractDictionaryTest {

    @BeforeAll
    public void setUp() {
        dictionary = new MerriamWebsterDictionary(jsonRequest);

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.CAR_JSON_RESPONSE);

        result = dictionary.search(word);
    }

    @Test
    @DisplayName("getSearchedWord() should return correct value")
    void searchedWordValueShouldBeCorrect() {
        assertThat(result.getSearchedWord(), equalTo(word));
    }

    @Test
    @DisplayName("getLanguage() should return correct language")
    void theLanguageFieldShouldBeCorrect() {
        assertThat(result.getLanguage(), equalTo(DictionaryCollection.ENEN));
    }

    @Test
    @DisplayName("getLink() should return correct link")
    void theLinkFieldShouldBeCorrect() {
        assertThat(result.getLink(), equalTo("https://www.merriam-webster.com/"));
    }

    @Test
    @DisplayName("getName() should return correct name")
    void theNameFieldShouldBeCorrect() {
        assertThat(result.getName(), equalTo("merriam-webster"));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word car")
    void resultsShouldBeAsExpectedForWordCar() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.CAR_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.CAR_JSON_RESPONSE);

        assertThat(dictionary.search("car").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word test")
    void resultsShouldBeAsExpectedForWordTest() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.TEST_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.TEST_JSON_RESPONSE);

        assertThat(dictionary.search("test").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word house")
    void resultsShouldBeAsExpectedForWordHouse() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.HOUSE_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.HOUSE_JSON_RESPONSE);

        assertThat(dictionary.search("house").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word cancer")
    void resultsShouldBeAsExpectedForWordCancer() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.CANCER_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.CANCER_JSON_RESPONSE);

        assertThat(dictionary.search("cancer").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word fox")
    void resultsShouldBeAsExpectedForWordFox() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.FOX_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.FOX_JSON_RESPONSE);

        assertThat(dictionary.search("fox").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word and")
    void resultsShouldBeAsExpectedForWordAnd() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.AND_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.AND_JSON_RESPONSE);

        assertThat(dictionary.search("and").getResults(), is(asExpected));
    }

    @Test
    @DisplayName("getResults() should return expected list of results for word smart")
    void resultsShouldBeAsExpectedForWordSmart() {
        List<String> asExpected = MerriamWebsterDictionaryExpectedResults.SMART_EXPECTED_RESULT;

        when(jsonRequest.getRequest(anyString()))
                .thenReturn(MerriamWebsterDictionaryJsons.SMART_JSON_RESPONSE);

        assertThat(dictionary.search("and").getResults(), is(asExpected));
    }

    @ParameterizedTest(name = "when input is {0} getSearchedWord() should return {1}")
    @CsvSource(delimiter = '|', value = {
            "!?.,word.,!?|word", "\"word\"|word", "\\'word\\'|word", "؟(word)؟|word", "[{،word،}]|word"
    })
    void searchedWordShouldBeSanitised(String input, String asExpected) {
        SearchResult result = dictionary.search(input);

        assertThat(result.getSearchedWord(), is(asExpected));
    }

    @Test
    @DisplayName("throws an IllegalArgumentException when searched word is an empty string")
    void throwsIllegalArgumentExceptionWhenSearchedWordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> dictionary.search(""));
    }

    @Test
    @DisplayName("returns and empty list of results when empty JSON is returned")
    void returnsAnEmptyResultWhenIOExceptionIsThrownInJsoupRequest() {
        when(jsonRequest.getRequest(anyString())).thenReturn("[]");

        assertThat(dictionary.search(word).getResults(), is(Collections.emptyList()));
    }
}