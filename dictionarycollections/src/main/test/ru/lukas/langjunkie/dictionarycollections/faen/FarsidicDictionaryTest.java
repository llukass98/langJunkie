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
public class FarsidicDictionaryTest {
	static Dictionary notMockedDictionary;
	static Dictionary mockedDic;
	static Document doc;
	static SearchResult result;
	static String word;

	@BeforeClass
	public static void generalSetUp() throws Exception {
		notMockedDictionary = new FarsidicDictionary();
		doc = Jsoup.parse(html);
		word = "wonder";
		mockedDic = mock(FarsidicDictionary.class);

		doCallRealMethod().when(mockedDic).search(anyString());
		doCallRealMethod().when(mockedDic).sanitizeInput(anyString());
		when(mockedDic.makeRequest(any(), anyString())).thenReturn(doc);

		result = mockedDic.search(word);
	}

	@Test
	public void makeRequestMethodShouldNotBeUsedWithEmptySearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(FarsidicDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("");

		verify(emptySearchMock, never()).makeRequest(any(), any());
	}

	@Test
	public void makeRequestShouldNotBeUsedWithStringOfSpacesAsSearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(FarsidicDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("         ");

		verify(emptySearchMock, never()).makeRequest(any(), any());
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainDoubleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(FarsidicDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any(), any())).thenReturn(doc);
		searchWordWithQuotes.search("\"wonder\"");

		verify(searchWordWithQuotes).makeRequest(any(),
				argThat(s -> !s.contains("=%22")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainSingleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(FarsidicDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any(), any())).thenReturn(doc);
		searchWordWithQuotes.search("'wonder'");

		verify(searchWordWithQuotes).makeRequest(any(),
				argThat(s -> !s.contains("=%27")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainLeadingSpaces()
			throws Exception
	{
		Dictionary searchWordWithSpaces = mock(FarsidicDictionary.class);

		doCallRealMethod().when(searchWordWithSpaces).search(anyString());
		doCallRealMethod().when(searchWordWithSpaces).sanitizeInput(anyString());
		when(searchWordWithSpaces.makeRequest(any(), anyString())).thenReturn(doc);
		searchWordWithSpaces.search("       wonder");

		verify(searchWordWithSpaces).makeRequest(any(),
				argThat(s -> !s.contains("+")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainTrailingSpaces()
			throws Exception
	{
		Dictionary searchWordWithSpaces = mock(FarsidicDictionary.class);

		doCallRealMethod().when(searchWordWithSpaces).search(anyString());
		doCallRealMethod().when(searchWordWithSpaces).sanitizeInput(anyString());
		when(searchWordWithSpaces.makeRequest(any(), anyString())).thenReturn(doc);
		searchWordWithSpaces.search("wonder       ");

		verify(searchWordWithSpaces).makeRequest(any(),
				argThat(s -> !s.contains("+")));
	}

	@Test
	public void makeRequestMethodShouldBeUsed() throws Exception {
		verify(mockedDic).makeRequest(any(), anyString());
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
				equalTo("http://www.farsidic.com/en/Lang/FaEn"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getName(),
				equalTo("farsidic"));
	}

	static String html = "<span class=\"form-label\">Farsi Word</span></td><td class=\"input-cell k-rtl\" style=\"text-wrap: none;\"><input class=\"word-input\" dir=\"rtl\" id=\"searchBox\" name=\"SearchWord\" onkeypress=\"return ConvertKeyPress(this,event);\" style=\"width:65%;font-size:1.1em\" type=\"text\" value=\"عشق\" /><button type=\"submit\" class=\"btn btn-primary btn-my\" style=\"margin:auto 5px;\">Search</button><button type=\"button\" class=\"btn btn-warning btn-my\" onclick=\"clearAndFocus()\">Clear</button></td></tr><tr><td class=\"label-cell\"><label class=\"form-label\" for=\"exactWord\">Criteria</label></td><td class=\"input-cell\"><label class=\"control-text\"><input checked=\"checked\" data-val=\"true\" data-val-required=\"The Criteria field is required.\" id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Exact\" />Exact Word</label><label class=\"control-text\" style=\"margin: auto 25px\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Start\" />Starts With</label><label class=\"control-text\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Contain\" />Contains</label></td></tr></table></div><input data-val=\"true\" data-val-required=\"The ShowKeyboard field is required.\" id=\"ShowKeyboard\" name=\"ShowKeyboard\" type=\"hidden\" value=\"true\" /></form><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><!-- Content Link --><ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:15px\" data-ad-client=\"ca-pub-2654160499252485\" data-ad-slot=\"1443369263\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">flame,love,passion</span></div></div></div><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">love</span></div></div></div>";
}
