package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import ru.lukas.langjunkie.dictionarycollections.AbstractDictionaryTest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dmitry Lukashevich
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AbadisDictionary tests")
class AbadisDictionaryTest extends AbstractDictionaryTest {

    private static final String HTML = "<div class=\"lun boxBd boxMain boxFaEn\" t=\"ابتدای صفحه\"><div id=\"boxWrd\"><h1>عشق</h1><div class=\"boxLtr\">/~eSq/</div></div><hr><div class=\"boxLtr\"><i class=\"aud\" onclick=\"tts(&quot;love&quot;, &quot;us&quot;)\"><span class=\"ico icoAuUs\"></span></i> <i class=\"aud\" onclick=\"tts(&quot;love&quot;, &quot;uk&quot;)\"><span class=\"ico icoAuUk\"></span></i> &nbsp; &nbsp;<l w=\"love\">love</l><br><i class=\"aud\" onclick=\"tts(&quot;passion&quot;, &quot;us&quot;)\"><span class=\"ico icoAuUs\"></span></i> <i class=\"aud\" onclick=\"tts(&quot;passion&quot;, &quot;uk&quot;)\"><span class=\"ico icoAuUk\"></span></i> &nbsp; &nbsp;<l w=\"passion\">passion</l><br><i class=\"aud\" onclick=\"tts(&quot;flame&quot;, &quot;us&quot;)\"><span class=\"ico icoAuUs\"></span></i> <i class=\"aud\" onclick=\"tts(&quot;flame&quot;, &quot;uk&quot;)\"><span class=\"ico icoAuUk\"></span></i> &nbsp; &nbsp;<l w=\"flame\">flame</l><br></div><div id=\"meanTools\"><i class=\"ico icoSt\" onclick=\"starWm()\"></i><i class=\"ico icoCo\" onclick=\"copyWm()\"></i><cgr id=\"msgTools\"></cgr></div></div>";

    @BeforeAll
    public void setUp() {
        dictionary = new AbadisDictionary(jsoupRequest);

        when(jsoupRequest.getRequest(anyString())).thenReturn(Jsoup.parse(HTML));

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
        assertThat(result.getLanguage(), equalTo(DictionaryCollection.FAEN));
    }

    @Test
    @DisplayName("getLink() should return correct link")
    void theLinkFieldShouldBeCorrect() {
        assertThat(result.getLink(), equalTo("https://abadis.ir"));
    }

    @Test
    @DisplayName("getName() should return correct name")
    void theNameFieldShouldBeCorrect() {
        assertThat(result.getName(), equalTo("abadis"));
    }

    @Test
    @DisplayName("getResults() should return expected list of results")
    void resultsValueShouldBeArrayOfStrings() {
        List<String> asExpected = List.of("love", "passion", "flame");

        assertThat(result.getResults(), is(asExpected));
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
    @DisplayName("returns and empty list of results when empty document is returned")
    void returnsAnEmptyResultWhenIOExceptionIsThrownInJsoupRequest() {
        when(jsoupRequest.getRequest(anyString())).thenReturn(new Document(""));

        assertThat(dictionary.search(word).getResults(), is(Collections.emptyList()));
    }
}
