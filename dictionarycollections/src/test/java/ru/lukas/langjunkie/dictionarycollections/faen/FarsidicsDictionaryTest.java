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
public class FarsidicsDictionaryTest {

	private Dictionary dictionary;
	private SearchResult result;
	private final String word = "wonder";

	@BeforeAll
	public void generalSetUp() throws Exception {
		Request<Document> jsoupRequest = mock(JsoupRequest.class);
		Document doc = Jsoup.parse(html);
		dictionary =  new FarsidicsDictionary(jsoupRequest);

		when(jsoupRequest.getRequest(anyString())).thenReturn(doc);

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
				equalTo("http://www.farsidics.com"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(dictionary.getName(),
				equalTo("farsidics"));
	}

	static String html = "<p><strong><div align=right dir=rtl>عشق‌</div></strong> flame, love, passion</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ گرایی‌</div></strong> romanticism</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و نفرت‌ توامان‌</div></strong> love-hate</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و علاقه‌</div></strong> fondness</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزی‌ کردن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزیدن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ نوجوانی‌ (عامیانه‌)</div></strong> calf love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آمیخته‌ با احترام‌</div></strong> adoration</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آزاد</div></strong> free love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بچگانه‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ والدین‌</div></strong> piety</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ همنوع‌</div></strong> caritas</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ مسافرت‌</div></strong> wanderIust</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ دارایی‌</div></strong> avarice</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بازی‌ کردن‌ بی‌ پروا (خودمانی‌)</div></strong> swing</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ دوست‌</div></strong> amorous</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ داشتن‌</div></strong> care</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ زودگذر(عامیانه‌)</div></strong> crush</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ سطحی‌ و زودرس‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقی‌</div></strong> amatory, dilettante, idyllic, romantic</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ (گیاه‌ شناسی‌)</div></strong> ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ زهرین‌ (گیاه‌ شناسی‌)</div></strong> poison ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌</div></strong> court, courtship</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌ کردن‌</div></strong> court, woo</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/>";
}
