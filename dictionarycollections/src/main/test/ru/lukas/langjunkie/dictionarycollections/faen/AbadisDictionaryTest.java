package ru.lukas.langjunkie.dictionarycollections.faen;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.doCallRealMethod;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollection.dictionary.SearchResult;

@RunWith(MockitoJUnitRunner.class)
public class AbadisDictionaryTest {
	static Dictionary notMockedDictionary;
	static Dictionary mockedDic;
	static Document doc;
	static SearchResult result;
	static String word;

	@BeforeClass
	public static void generalSetUp() throws Exception {
		notMockedDictionary = new AbadisDictionary();
		doc = Jsoup.parse(html);
		word = new StringBuilder("عشق").reverse().toString();
		mockedDic = mock(AbadisDictionary.class);

		doCallRealMethod().when(mockedDic).search(anyString());
		doCallRealMethod().when(mockedDic).sanitizeInput(anyString());
		when(mockedDic.makeRequest(any())).thenReturn(doc);

		result = mockedDic.search(word);
	}

	@Test
	public void makeRequestMethodShouldNotBeUsedWithEmptySearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(AbadisDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("");

		verify(emptySearchMock, never()).makeRequest(any());
	}

	@Test
	public void makeRequestShouldNotBeUsedWithStringOfSpacesAsSearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(AbadisDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("         ");

		verify(emptySearchMock, never()).makeRequest(any());
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainDoubleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(AbadisDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any())).thenReturn(doc);
		searchWordWithQuotes.search("\"wonder\"");

		verify(searchWordWithQuotes).makeRequest(argThat(s -> !s.contains("=\"")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainSingleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(AbadisDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any())).thenReturn(doc);
		searchWordWithQuotes.search("'wonder'");

		verify(searchWordWithQuotes).makeRequest(argThat(s -> !s.contains("='")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainLeadingSpaces()
			throws Exception
	{
		Dictionary searchWordWithSpaces = mock(AbadisDictionary.class);

		doCallRealMethod().when(searchWordWithSpaces).search(anyString());
		doCallRealMethod().when(searchWordWithSpaces).sanitizeInput(anyString());
		when(searchWordWithSpaces.makeRequest(any())).thenReturn(doc);
		searchWordWithSpaces.search("       wonder");

		verify(searchWordWithSpaces).makeRequest(argThat(s -> !s.contains(" ")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainTrailingSpaces()
			throws Exception
	{
		Dictionary searchWordWithSpaces = mock(AbadisDictionary.class);

		doCallRealMethod().when(searchWordWithSpaces).search(anyString());
		doCallRealMethod().when(searchWordWithSpaces).sanitizeInput(anyString());
		when(searchWordWithSpaces.makeRequest(any())).thenReturn(doc);
		searchWordWithSpaces.search("wonder       ");

		verify(searchWordWithSpaces).makeRequest(argThat(s -> !s.contains(" ")));
	}

	@Test
	public void makeRequestMethodShouldBeUsed() throws Exception {
		verify(mockedDic).makeRequest(any());
	}

	@Test
	public void parseSynonymsMethodShouldBeUsed() {
		verify(mockedDic).parseSynonyms(any());
	}

	@Test
	public void parseExamplesMethodShouldBeUsed() {
		verify(mockedDic).parseExamples(any());
	}

	@Test
	public void searchedWordValueShouldBeCorrect() {
		assertThat(result.getSearchedWord(), equalTo(word));
	}

	@Test
	public void resultsValueShouldBeArrayOfStrings() {
		ArrayList<String> results = (ArrayList<String>) result.getResults();
		ArrayList<String> asExpected = new ArrayList<>();

		asExpected.add("love");
		asExpected.add("passion");
		asExpected.add("flame");

		assertThat(results, is(asExpected));
	}

	@Test
	public void examplesValueShouldBeArrayOfStrings() {
		String exampleStart = new StringBuilder("عشق آباد").reverse().toString();
		String exampleEnd = new StringBuilder("عشق ورزی").reverse().toString();
		String randomValue = new StringBuilder("عشق به دارایی").reverse().toString();
		List<String> examples = notMockedDictionary.parseExamples(doc);

		assertThat(examples.get(0), equalTo(exampleStart));
		assertThat(examples.get(examples.size()-1), equalTo(exampleEnd));
		assertThat(examples, hasItem(randomValue));
	}

	@Test
	public void synonymsValueShouldBeArrayOfStrings() {
		String synonymStart = new StringBuilder("خاطرخواهی").reverse().toString();
		String synonymEnd = new StringBuilder("دیوانگی").reverse().toString();
		String randomValue = new StringBuilder("احساسات تند و شدید").reverse().toString();
		List<String> synonyms = notMockedDictionary.parseSynonyms(doc);

		assertThat(synonyms.get(0), equalTo(synonymStart));
		assertThat(synonyms.get(synonyms.size()-1), equalTo(synonymEnd));
		assertThat(synonyms, hasItem(randomValue));
	}

	@Test
	public void synonymsValueShouldNotContainSearchedWord() {
		List<String> synonyms = notMockedDictionary.parseSynonyms(doc);

		assertThat(synonyms, not(hasItem(word)));
	}

	@Test
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getLanguage(),
				equalTo("faen"));
	}

	@Test
	public void theLinkFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getLink(),
				equalTo("https://abadis.ir"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getName(),
				equalTo("abadis"));
	}

	static String html = "<div id=\"Means\"><aba i=\"0\" style=\"height:380px\"><div class=\"AbaRow\" style=\"height:250px\"><a href=\"http://transnet.ir/?utm_source=abadis&utm_medium=banner\" target=\"blank\" rel=\"nofollow noopener\"><img src=\"/images/ads/transnet-970-250.gif\" alt=\"ناریا نیمجرتم هکبش\" width=\"100%\" /></a></div><div class=\"AbaRow\" style=\"height:130px\"><div class=\"yn-bnr\" id=\"ynpos-6456\"></div></div></aba><div class=\"Lun\" title=\"همه\"><div class=\"MainMeanBox\"><h1>قشع</h1><hr /><table class=\"MeanAudioFa\" width=\"100%\"dir=\"ltr\"><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"love\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"love\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/l/love/\">love</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"passion\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"passion\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/p/passion/\">passion</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"flame\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"flame\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/f/flame/\">flame</a></div></td></tr><tr></tr></table></div><div class=\"MeanMore\"><a href=\"javascript:StarWordMean()\"><span class=\"Icons IcoStar\"></span></a><div class=\"WordMsg\"></div></div></div><div class=\"Lun\" title=\"یسیلگنا هب یسراف\"><div class=\"BoxTitle\"><label>یسیلگنا هب یسراف</label></div><div class=\"BoxBody\" id=\"FaToEn\"><div class=\"WordB\">دابآ قشع</div><div class=\"Mean\">askabad or ashqabad</div><div class=\"WordB\">دازآ قشع</div><div class=\"Mean\">free love</div><div class=\"WordB\">مارتحا اب هتخیمآ قشع</div><div class=\"Mean\">adoration</div><div class=\"WordB\">زیگنا قشع</div><div class=\"Mean\">exciting love</div><div class=\"WordB\">اورپ یب ندرک یزاب قشع</div><div class=\"Mean\">swing</div><div class=\"WordB\">هناگچب قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییاراد هب قشع</div><div class=\"Mean\">avarice</div><div class=\"WordB\">ترفاسم هب قشع</div><div class=\"Mean\">wanderiust</div><div class=\"WordB\">عونمه هب قشع</div><div class=\"Mean\">caritas</div><div class=\"WordB\">نیدلاو هب قشع</div><div class=\"Mean\">piety</div><div class=\"WordB\">نتشاد قشع</div><div class=\"Mean\">care</div><div class=\"WordB\">تسود قشع</div><div class=\"Mean\">amorous</div><div class=\"WordB\">رذگدوز قشع</div><div class=\"Mean\">crush</div><div class=\"WordB\">سردوز و یحطس قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییارگ قشع</div><div class=\"Mean\">romanticism</div><div class=\"WordB\">یناوجون قشع</div><div class=\"Mean\">calf love</div><div class=\"WordB\">هقالع و قشع</div><div class=\"Mean\">fondness</div><div class=\"WordB\">ناماوت ترفن و قشع</div><div class=\"Mean\">love-hate</div><div class=\"WordB\">یزرو قشع</div>love-making<b class=\"HasMore\" title=\"قشع\" nd=\"FaToEn\" np=\"2\">...</b></div></div><aba i=\"1\" style=\"height:550px\"><div class=\"AbaRow Yek970\" style=\"height:250px\"><div class=\"yn-bnr\" id=\"ynpos-6267\"></div></div><div class=\"AbaRow YekNative\" style=\"height:300px\"><div id=\"pos-article-display-2779\"></div></div></aba><div class=\"Lun\" title=\"فدارتم\"><div class=\"BoxTitle\"><label>فدارتم</label></div><div class=\"BoxBody\" id=\"FaToEnSyn\"><div class=\"WordA\">love<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یهاوخرطاخ ،هقوشعم ،قشع ،رهم ،تبحم ،ینابرهم</div><div class=\"WordA\">amour<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">قشع</div><div class=\"WordA\">passion<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یناسفن ضارغا ،دیدش تاساسحا ،دیدش بصعت ،دیدش و دنت تاساسحا ،یوه ،دیدش هقالع و قایتشا ،سفن یاوه ،روش ،قشع</div><div class=\"WordA\">mania<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">دایز و لیلد یب ناجیه ،ینالقع ریغ یدنمقالع ،ییادیش ،قشع ،یگناوید</div></div></div></div>";
}
