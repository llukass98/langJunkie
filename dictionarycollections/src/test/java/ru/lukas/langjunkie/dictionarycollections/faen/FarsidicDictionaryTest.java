package ru.lukas.langjunkie.dictionarycollections.faen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.lukas.langjunkie.dictionarycollections.dictionary.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FarsidicDictionaryTest {

	private Dictionary dictionary;
	private SearchResult result;
	private final String word = "wonder";

	@BeforeAll
	public void generalSetUp() throws Exception {
		Request<Document> jsoupRequest = mock(JsoupRequest.class);
		Document doc = Jsoup.parse(html);
		dictionary = new FarsidicDictionary(jsoupRequest);

		when(jsoupRequest.postRequest(anyString(), anyString())).thenReturn(doc);

		result = dictionary.search(word);
	}

	@Test
	public void searchedWordValueShouldBeCorrect() {
		assertThat(result.getSearchedWord(), equalTo(word));
	}

	@Test
	public void resultsValueShouldBeArrayOfStrings() {
		List<String> results = result.getResults();
		List<String> asExpected = List.of("flame", "love", "passion");

		assertThat(results, is(asExpected));
	}

	@Test
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(dictionary.getLanguage(),
				equalTo(DictionaryCollection.FAEN));
	}

	@Test
	public void theLinkFieldShouldBeCorrect() {
		assertThat(dictionary.getLink(),
				equalTo("http://www.farsidic.com/en/Lang/FaEn"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(dictionary.getName(),
				equalTo("farsidic"));
	}

	static String html = "<span class=\"form-label\">Farsi Word</span></td><td class=\"input-cell k-rtl\" style=\"text-wrap: none;\"><input class=\"word-input\" dir=\"rtl\" id=\"searchBox\" name=\"SearchWord\" onkeypress=\"return ConvertKeyPress(this,event);\" style=\"width:65%;font-size:1.1em\" type=\"text\" value=\"عشق\" /><button type=\"submit\" class=\"btn btn-primary btn-my\" style=\"margin:auto 5px;\">Search</button><button type=\"button\" class=\"btn btn-warning btn-my\" onclick=\"clearAndFocus()\">Clear</button></td></tr><tr><td class=\"label-cell\"><label class=\"form-label\" for=\"exactWord\">Criteria</label></td><td class=\"input-cell\"><label class=\"control-text\"><input checked=\"checked\" data-val=\"true\" data-val-required=\"The Criteria field is required.\" id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Exact\" />Exact Word</label><label class=\"control-text\" style=\"margin: auto 25px\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Start\" />Starts With</label><label class=\"control-text\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Contain\" />Contains</label></td></tr></table></div><input data-val=\"true\" data-val-required=\"The ShowKeyboard field is required.\" id=\"ShowKeyboard\" name=\"ShowKeyboard\" type=\"hidden\" value=\"true\" /></form><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><!-- Content Link --><ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:15px\" data-ad-client=\"ca-pub-2654160499252485\" data-ad-slot=\"1443369263\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">flame,love,passion</span></div></div></div><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">love</span></div></div></div>";
}
