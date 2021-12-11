package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.Jsoup;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.JsoupRequest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbadisDictionaryTest {

	private Dictionary dictionary;
	private SearchResult result;
	private final String word = new StringBuilder("عشق").reverse().toString();


	@BeforeAll
	public void generalSetUp() throws IOException {
		Request<Document> jsoupRequest = mock(JsoupRequest.class);
		Document doc = Jsoup.parse(html);
		dictionary = new AbadisDictionary(jsoupRequest);

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
		List<String> asExpected = List.of("love", "passion", "flame");

		assertThat(results, equalTo(asExpected));
	}

	@Test
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(dictionary.getLanguage(),
				equalTo("faen"));
	}

	@Test
	public void theLinkFieldShouldBeCorrect() {
		assertThat(dictionary.getLink(),
				equalTo("https://abadis.ir"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(dictionary.getName(),
				equalTo("abadis"));
	}

	static String html = "<div id=\"Means\"><aba i=\"0\" style=\"height:380px\"><div class=\"AbaRow\" style=\"height:250px\"><a href=\"http://transnet.ir/?utm_source=abadis&utm_medium=banner\" target=\"blank\" rel=\"nofollow noopener\"><img src=\"/images/ads/transnet-970-250.gif\" alt=\"ناریا نیمجرتم هکبش\" width=\"100%\" /></a></div><div class=\"AbaRow\" style=\"height:130px\"><div class=\"yn-bnr\" id=\"ynpos-6456\"></div></div></aba><div class=\"Lun\" title=\"همه\"><div class=\"MainMeanBox\"><h1>قشع</h1><hr /><table class=\"MeanAudioFa\" width=\"100%\"dir=\"ltr\"><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"love\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"love\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/l/love/\">love</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"passion\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"passion\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/p/passion/\">passion</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"flame\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"flame\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/f/flame/\">flame</a></div></td></tr><tr></tr></table></div><div class=\"MeanMore\"><a href=\"javascript:StarWordMean()\"><span class=\"Icons IcoStar\"></span></a><div class=\"WordMsg\"></div></div></div><div class=\"Lun\" title=\"یسیلگنا هب یسراف\"><div class=\"BoxTitle\"><label>یسیلگنا هب یسراف</label></div><div class=\"BoxBody\" id=\"FaToEn\"><div class=\"WordB\">دابآ قشع</div><div class=\"Mean\">askabad or ashqabad</div><div class=\"WordB\">دازآ قشع</div><div class=\"Mean\">free love</div><div class=\"WordB\">مارتحا اب هتخیمآ قشع</div><div class=\"Mean\">adoration</div><div class=\"WordB\">زیگنا قشع</div><div class=\"Mean\">exciting love</div><div class=\"WordB\">اورپ یب ندرک یزاب قشع</div><div class=\"Mean\">swing</div><div class=\"WordB\">هناگچب قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییاراد هب قشع</div><div class=\"Mean\">avarice</div><div class=\"WordB\">ترفاسم هب قشع</div><div class=\"Mean\">wanderiust</div><div class=\"WordB\">عونمه هب قشع</div><div class=\"Mean\">caritas</div><div class=\"WordB\">نیدلاو هب قشع</div><div class=\"Mean\">piety</div><div class=\"WordB\">نتشاد قشع</div><div class=\"Mean\">care</div><div class=\"WordB\">تسود قشع</div><div class=\"Mean\">amorous</div><div class=\"WordB\">رذگدوز قشع</div><div class=\"Mean\">crush</div><div class=\"WordB\">سردوز و یحطس قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییارگ قشع</div><div class=\"Mean\">romanticism</div><div class=\"WordB\">یناوجون قشع</div><div class=\"Mean\">calf love</div><div class=\"WordB\">هقالع و قشع</div><div class=\"Mean\">fondness</div><div class=\"WordB\">ناماوت ترفن و قشع</div><div class=\"Mean\">love-hate</div><div class=\"WordB\">یزرو قشع</div>love-making<b class=\"HasMore\" title=\"قشع\" nd=\"FaToEn\" np=\"2\">...</b></div></div><aba i=\"1\" style=\"height:550px\"><div class=\"AbaRow Yek970\" style=\"height:250px\"><div class=\"yn-bnr\" id=\"ynpos-6267\"></div></div><div class=\"AbaRow YekNative\" style=\"height:300px\"><div id=\"pos-article-display-2779\"></div></div></aba><div class=\"Lun\" title=\"فدارتم\"><div class=\"BoxTitle\"><label>فدارتم</label></div><div class=\"BoxBody\" id=\"FaToEnSyn\"><div class=\"WordA\">love<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یهاوخرطاخ ،هقوشعم ،قشع ،رهم ،تبحم ،ینابرهم</div><div class=\"WordA\">amour<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">قشع</div><div class=\"WordA\">passion<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یناسفن ضارغا ،دیدش تاساسحا ،دیدش بصعت ،دیدش و دنت تاساسحا ،یوه ،دیدش هقالع و قایتشا ،سفن یاوه ،روش ،قشع</div><div class=\"WordA\">mania<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">دایز و لیلد یب ناجیه ،ینالقع ریغ یدنمقالع ،ییادیش ،قشع ،یگناوید</div></div></div></div>";
}