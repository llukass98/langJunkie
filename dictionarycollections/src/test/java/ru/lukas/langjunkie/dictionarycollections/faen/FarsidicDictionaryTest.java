package ru.lukas.langjunkie.dictionarycollections.faen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

/**
 * @author Dmitry Lukashevich
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("FarsidicDictionary tests")
class FarsidicDictionaryTest extends AbstractDictionaryTest {

	private final static String HTML = "<span class=\"form-label\">Farsi Word</span></td><td class=\"input-cell k-rtl\" style=\"text-wrap: none;\"><input class=\"word-input\" dir=\"rtl\" id=\"searchBox\" name=\"SearchWord\" onkeypress=\"return ConvertKeyPress(this,event);\" style=\"width:65%;font-size:1.1em\" type=\"text\" value=\"عشق\" /><button type=\"submit\" class=\"btn btn-primary btn-my\" style=\"margin:auto 5px;\">Search</button><button type=\"button\" class=\"btn btn-warning btn-my\" onclick=\"clearAndFocus()\">Clear</button></td></tr><tr><td class=\"label-cell\"><label class=\"form-label\" for=\"exactWord\">Criteria</label></td><td class=\"input-cell\"><label class=\"control-text\"><input checked=\"checked\" data-val=\"true\" data-val-required=\"The Criteria field is required.\" id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Exact\" />Exact Word</label><label class=\"control-text\" style=\"margin: auto 25px\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Start\" />Starts With</label><label class=\"control-text\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Contain\" />Contains</label></td></tr></table></div><input data-val=\"true\" data-val-required=\"The ShowKeyboard field is required.\" id=\"ShowKeyboard\" name=\"ShowKeyboard\" type=\"hidden\" value=\"true\" /></form><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><!-- Content Link --><ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:15px\" data-ad-client=\"ca-pub-2654160499252485\" data-ad-slot=\"1443369263\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">flame,love,passion</span></div></div></div><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">love</span></div></div></div>";

	@BeforeAll
	public void setUp() throws Exception {
		dictionary = new FarsidicDictionary(jsoupRequest);

		when(jsoupRequest.postRequest(anyString(), anyString())).thenReturn(Jsoup.parse(HTML));

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
		assertThat(result.getLink(), equalTo("http://www.farsidic.com/en/Lang/FaEn"));
	}

	@Test
	@DisplayName("getName() should return correct name")
	void theNameFieldShouldBeCorrect() {
		assertThat(result.getName(), equalTo("farsidic"));
	}

	@Test
	@DisplayName("getResults() should return expected list of results")
	void resultsValueShouldBeArrayOfStrings() {
		List<String> asExpected = List.of("flame", "love", "passion");

		assertThat(result.getResults(), is(asExpected));
	}

	@ParameterizedTest(name = "when input is {0} getSearchedWord() should return {1}")
	@DisplayName("searched word should be clear of any non-letter character")
	@CsvSource(delimiter = '|', value = {
			"!?.,word.,!?|word", "\"word\"|word", "\\'word\\'|word", "؟(word)؟|word", "[{،word،}]|word"
	})
	void searchedWordShouldBeSanitised(String input, String expected) {
		SearchResult result = dictionary.search(input);

		assertThat(result.getSearchedWord(), equalTo(expected));
	}

	@Test
	@DisplayName("throws an IllegalArgumentException when searched word is an empty string")
	void throwsIllegalArgumentExceptionWhenSearchedWordIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> dictionary.search(""));
	}

	@Test
	@DisplayName("returns and empty list of results when IOException is thrown in jsoupRequest")
	void returnsAnEmptyResultWhenIOExceptionIsThrownInJsoupRequest()
			throws IOException
	{
		when(jsoupRequest.postRequest(anyString(), anyString())).thenThrow(IOException.class);

		assertThat(dictionary.search(word).getResults(), is(Collections.emptyList()));
	}
}
