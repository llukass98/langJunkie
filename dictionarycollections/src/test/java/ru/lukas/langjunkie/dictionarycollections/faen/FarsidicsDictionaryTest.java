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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("FarsidicsDictionary tests")
public class FarsidicsDictionaryTest extends AbstractDictionaryTest {

	private final static String HTML = "<p><strong><div align=right dir=rtl>عشق‌</div></strong> flame, love, passion</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ گرایی‌</div></strong> romanticism</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و نفرت‌ توامان‌</div></strong> love-hate</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و علاقه‌</div></strong> fondness</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزی‌ کردن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزیدن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ نوجوانی‌ (عامیانه‌)</div></strong> calf love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آمیخته‌ با احترام‌</div></strong> adoration</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آزاد</div></strong> free love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بچگانه‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ والدین‌</div></strong> piety</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ همنوع‌</div></strong> caritas</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ مسافرت‌</div></strong> wanderIust</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ دارایی‌</div></strong> avarice</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بازی‌ کردن‌ بی‌ پروا (خودمانی‌)</div></strong> swing</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ دوست‌</div></strong> amorous</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ داشتن‌</div></strong> care</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ زودگذر(عامیانه‌)</div></strong> crush</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ سطحی‌ و زودرس‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقی‌</div></strong> amatory, dilettante, idyllic, romantic</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ (گیاه‌ شناسی‌)</div></strong> ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ زهرین‌ (گیاه‌ شناسی‌)</div></strong> poison ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌</div></strong> court, courtship</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌ کردن‌</div></strong> court, woo</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/>";

	@BeforeAll
	public void setUp() throws Exception {
		dictionary =  new FarsidicsDictionary(jsoupRequest);

		when(jsoupRequest.getRequest(anyString())).thenReturn(Jsoup.parse(HTML));

		result = dictionary.search(word);
	}

	@Test
	@DisplayName("getSearchedWord() should return correct value")
	public void searchedWordValueShouldBeCorrect() {
		assertThat(result.getSearchedWord(), equalTo(word));
	}

	@Test
	@DisplayName("getLanguage() should return correct language")
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(result.getLanguage(), equalTo(DictionaryCollection.FAEN));
	}

	@Test
	@DisplayName("getLink() should return correct link")
	public void theLinkFieldShouldBeCorrect() {
		assertThat(result.getLink(), equalTo("http://www.farsidics.com"));
	}

	@Test
	@DisplayName("getName() should return correct name")
	public void theNameFieldShouldBeCorrect() {
		assertThat(result.getName(), equalTo("farsidics"));
	}

	@Test
	@DisplayName("getResults() should return expected list of results")
	public void resultsValueShouldBeArrayOfStrings() {
		List<String> asExpected = List.of("flame", "love", "passion");

		assertThat(result.getResults(), is(asExpected));
	}

	@ParameterizedTest(name = "when input is {0} getSearchedWord() should return {1}")
	@DisplayName("searched word should be clear of any non-letter character")
	@CsvSource(delimiter = '|', value = {
			"!?.,word.,!?|word", "\"word\"|word", "\\'word\\'|word", "؟(word)؟|word", "[{،word،}]|word"
	})
	public void searchedWordShouldBeSanitised(String input, String expected) {
		SearchResult result = dictionary.search(input);

		assertThat(result.getSearchedWord(), equalTo(expected));
	}

	@Test
	@DisplayName("throws an IllegalArgumentException when searched word is an empty string")
	public void throwsIllegalArgumentExceptionWhenSearchedWordIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> dictionary.search(""));
	}

	@Test
	@DisplayName("returns and empty list of results when IOException is thrown in jsoupRequest")
	public void returnsAnEmptyResultWhenIOExceptionIsThrownInJsoupRequest()
			throws IOException
	{
		when(jsoupRequest.getRequest(anyString())).thenThrow(IOException.class);

		assertThat(dictionary.search(word).getResults(), is(Collections.emptyList()));
	}
}
