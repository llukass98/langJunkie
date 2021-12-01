package ru.lukas.langjunkie.dictionarycollections.faen;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.argThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;

import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollection.dictionary.SearchResult;

@RunWith(MockitoJUnitRunner.class)
public class FarsidicsDictionaryTest {
	static Dictionary notMockedDictionary;
	static Dictionary mockedDic;
	static Document doc;
	static SearchResult result;
	static String word;

	@BeforeClass
	public static void generalSetUp() throws Exception {
		notMockedDictionary = new FarsidicsDictionary();
		doc = Jsoup.parse(html);
		word = "wonder";
		mockedDic = mock(FarsidicsDictionary.class);

		doCallRealMethod().when(mockedDic).search(anyString());
		doCallRealMethod().when(mockedDic).sanitizeInput(anyString());
		when(mockedDic.makeRequest(any())).thenReturn(doc);

		result = mockedDic.search(word);
	}

	@Test
	public void makeRequestMethodShouldNotBeUsedWithEmptySearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(FarsidicsDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("");

		verify(emptySearchMock, never()).makeRequest(any());
	}

	@Test
	public void makeRequestShouldNotBeUsedWithStringOfSpacesAsSearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(FarsidicsDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("         ");

		verify(emptySearchMock, never()).makeRequest(any());
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainDoubleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(FarsidicsDictionary.class);

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
		Dictionary searchWordWithQuotes = mock(FarsidicsDictionary.class);

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
		Dictionary searchWordWithSpaces = mock(FarsidicsDictionary.class);

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
		Dictionary searchWordWithSpaces = mock(FarsidicsDictionary.class);

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
	public void searchedWordValueShouldBeCorrect() {
		assertThat(result.getSearchedWord(), equalTo(word));
	}

	@Test
	public void resultsValueShouldBeArrayOfStrings() {
		ArrayList<String> results = (ArrayList<String>) result.getResults();
		ArrayList<String> asExpected = new ArrayList<>();

		asExpected.add("flame");
		asExpected.add("love");
		asExpected.add("passion");

		assertThat(results, is(asExpected));
	}

	@Test
	public void examplesValueShouldBeAnEmptyArray() {
		assertThat(result.getExamples(),
				equalTo(Collections.emptyList()));
	}

	@Test
	public void synonymsValueShouldBeAnEmptyArray() {
		assertThat(result.getSynonyms(),
				equalTo(Collections.emptyList()));
	}

	@Test
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getLanguage(),
				equalTo("faen"));
	}

	@Test
	public void theLinkFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getLink(),
				equalTo("http://www.farsidics.com"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getName(),
				equalTo("farsidics"));
	}

	static String html = "<p><strong><div align=right dir=rtl>عشق‌</div></strong> flame, love, passion</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ گرایی‌</div></strong> romanticism</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و نفرت‌ توامان‌</div></strong> love-hate</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ و علاقه‌</div></strong> fondness</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزی‌ کردن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ ورزیدن‌</div></strong> love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ نوجوانی‌ (عامیانه‌)</div></strong> calf love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آمیخته‌ با احترام‌</div></strong> adoration</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ آزاد</div></strong> free love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بچگانه‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ والدین‌</div></strong> piety</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ همنوع‌</div></strong> caritas</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ مسافرت‌</div></strong> wanderIust</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ به‌ دارایی‌</div></strong> avarice</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ بازی‌ کردن‌ بی‌ پروا (خودمانی‌)</div></strong> swing</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ دوست‌</div></strong> amorous</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ داشتن‌</div></strong> care</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ زودگذر(عامیانه‌)</div></strong> crush</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشق‌ سطحی‌ و زودرس‌</div></strong> puppy love</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقی‌</div></strong> amatory, dilettante, idyllic, romantic</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ (گیاه‌ شناسی‌)</div></strong> ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقه‌ زهرین‌ (گیاه‌ شناسی‌)</div></strong> poison ivy</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌</div></strong> court, courtship</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/><p><strong><div align=right dir=rtl>عشقبازی‌ کردن‌</div></strong> court, woo</p><div class=\"prodtopline\" style=\"border-bottom :1px solid #EDEDED;height:30px;width:100%;margin:0px auto;border-color:#333;\"> </div><br/>";
}
