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
public class DictionaryFarsiDictionaryTest {
	static Dictionary notMockedDictionary;
	static Dictionary mockedDic;
	static Document doc;
	static SearchResult result;
	static String word;

	@BeforeClass
	public static void generalSetUp() throws Exception {
		notMockedDictionary = new DictionaryFarsiDictionary();
		doc = Jsoup.parse(html);
		word = "wonder";
		mockedDic = mock(DictionaryFarsiDictionary.class);

		doCallRealMethod().when(mockedDic).search(anyString());
		doCallRealMethod().when(mockedDic).sanitizeInput(anyString());
		when(mockedDic.makeRequest(any(), anyString())).thenReturn(doc);

		result = mockedDic.search(word);
	}

	@Test
	public void makeRequestMethodShouldNotBeUsedWithEmptySearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(DictionaryFarsiDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("");

		verify(emptySearchMock, never()).makeRequest(any(), any());
	}

	@Test
	public void makeRequestShouldNotBeUsedWithStringOfSpacesAsSearchWord()
			throws Exception
	{
		Dictionary emptySearchMock = mock(DictionaryFarsiDictionary.class);

		doCallRealMethod().when(emptySearchMock).search(anyString());
		doCallRealMethod().when(emptySearchMock).sanitizeInput(anyString());
		emptySearchMock.search("         ");

		verify(emptySearchMock, never()).makeRequest(any(), any());
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainDoubleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(DictionaryFarsiDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any(), anyString())).thenReturn(doc);
		searchWordWithQuotes.search("\"wonder\"");

		verify(searchWordWithQuotes).makeRequest(any(),
				argThat(s -> !s.contains("=%22")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainSingleQuotes()
			throws Exception
	{
		Dictionary searchWordWithQuotes = mock(DictionaryFarsiDictionary.class);

		doCallRealMethod().when(searchWordWithQuotes).search(anyString());
		doCallRealMethod().when(searchWordWithQuotes).sanitizeInput(anyString());
		when(searchWordWithQuotes.makeRequest(any(), anyString())).thenReturn(doc);
		searchWordWithQuotes.search("'wonder'");

		verify(searchWordWithQuotes).makeRequest(any(),
				argThat(s -> !s.contains("=%27")));
	}

	@Test
	public void searchedWordInMakeRequestShouldNotContainLeadingSpaces()
			throws Exception
	{
		Dictionary searchWordWithSpaces = mock(DictionaryFarsiDictionary.class);

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
		Dictionary searchWordWithSpaces = mock(DictionaryFarsiDictionary.class);

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

		asExpected.add("love");
		asExpected.add("love affair");
		asExpected.add("the gentle passion");
		asExpected.add("affection");
		asExpected.add("amour");
		asExpected.add("passion");
		asExpected.add("furor");
		asExpected.add("-phile");
		asExpected.add("philo-");

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
				equalTo("http://www.dictionary-farsi.com"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(notMockedDictionary.getName(),
				equalTo("dictionary-farsi"));
	}

	static String html = "<form name=\"form1\" action=\"/\" method=\"post\"><input type=\"hidden\" value=\"ok\" name=\"submith\" /><center><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"white\" width=\"100%\"><tr><td width=\"100%\" align=\"center\"><table border=\"0\" width=\"728\" cellspacing=\"0\" cellpadding=\"0\"><tr><td width=\"100%\" colspan=\"2\"><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr><td width=\"100%\" align=\"center\"><table border=\"0\" cellpadding=\"0\" width=\"100%\" cellspacing=\"0\" class=\"abc\"><tr><td valign=\"top\" align=\"left\" height=\"9\"><img border=\"0\" src=\"images/btl.gif\" width=\"2\" height=\"2\" /></td><td rowspan=\"2\" width=\"100%\" valign=\"middle\" align=\"center\"><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"5\" style=\"font-family:tahoma; font-size:12px\"><tr><td width=\"50%\" nowrap class=\"abc-td\" valign=\"middle\" align=\"right\">Please enter a Word:</td><td align=\"center\" valign=\"middle\"><input class=\"textbox1\" onfocus=\"select(this)\" type=\"text\" onkeyup=\"dic(this.value)\" autocomplete=\"off\" onkeypress=\"if(event.keyCode==13) Submit_Form(); else return convert(this.value, event)\" name=\"text1\" value=\"عشق\" /></td><td width=\"30%\"><input type=\"submit\" value=\"Search\" class=\"but1\" name=\"submit1\" id=\"searchBtn\" /></td></tr><tr><td class=\"abc-td\" align=\"right\">Search Type:</td><td align=\"center\" class=\"abc-td\"><input type=\"radio\" name=\"r1\" value=\"e2p\" onclick=\"e2p()\" /><span onclick=\"e2p()\">English to Farsi</span>&nbsp;<input type=\"radio\" checked name=\"r1\" value=\"p2e\" onclick=\"p2e()\" /><span onclick=\"p2e()\">Farsi to English</span></td></tr></table></td><td valign=\"top\" align=\"right\" height=\"9\"><img border=\"0\" src=\"images/btr.gif\" width=\"2\" height=\"2\" /></td></tr><tr><td valign=\"bottom\" align=\"left\" height=\"6\"><img border=\"0\" src=\"images/bbl.gif\" width=\"2\" height=\"2\" /></td><td valign=\"bottom\" align=\"right\" height=\"6\"><img border=\"0\" src=\"images/bbr.gif\" width=\"2\" height=\"2\" /></td></tr></table></td></tr><tr height=\"40\"><td align=\"center\" colspan=\"2\"><b><span id=\"txtHint\"></span></b></td></tr><tr><td align=\"center\"><img border=\"0\" height=\"160\" src=\"/Images/farsikeyboradb.gif\" usemap=\"#hashtdotcom1\" width=\"444\" id=\"hashtdotcom\" /><br /></td></tr></table></td></tr></table></td></tr></table><script language=\"javascript\">document.form1.text1.focus();mode=1;flag=0;dic(form1.text1.value);document.form1.text1.style.textAlign = \"right\";document.form1.text1.style.direction = \"rtl\";</script><table border=\"0\" width=\"728\" cellspacing=\"1\"><tr><td align=\"center\" colspan=\"3\" class=\"dividerbaner\">Result for : عشق ( in words )<div id=\"linkstr\"></div></td></tr><tr align=\"center\" class=\"tdtitr\"><td width=\"20\"><font face=\"Tahoma\" size=\"2\"><b>#</b></font></td><td width=\"66%\"><font face=\"Tahoma\" size=\"2\">English</font></td><td width=\"30%\"><font face=\"Tahoma\" size=\"2\">Farsi</font></td></tr><tr class=\"tddetail\" valign=\"top\"><td class=\"english\" align=\"center\"><b>1</b></td><td class=\"english\"><a id=\"0\" onmouseover=\"dic_Dic('love');Set_Background_Color(0);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(0);\">love</a>,<a id=\"1\" onmouseover=\"dic_Dic('love affair');Set_Background_Color(1);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(1);\">love affair</a>,<a id=\"2\" onmouseover=\"dic_Dic('the gentle passion');Set_Background_Color(2);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(2);\">the gentle passion</a>,<a id=\"3\" onmouseover=\"dic_Dic('affection');Set_Background_Color(3);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(3);\">affection</a>,<a id=\"4\" onmouseover=\"dic_Dic('amour');Set_Background_Color(4);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(4);\">amour</a>,<a id=\"5\" onmouseover=\"dic_Dic('passion');Set_Background_Color(5);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(5);\">passion</a>,<a id=\"6\" onmouseover=\"dic_Dic('furor');Set_Background_Color(6);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(6);\">furor</a>,<a id=\"7\" onmouseover=\"dic_Dic('-phile');Set_Background_Color(7);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(7);\">-phile</a>,<a id=\"8\" onmouseover=\"dic_Dic('philo-');Set_Background_Color(8);\" onmouseout=\"hideLayer(-50);Reset_Background_Color(8);\">philo-</a></td><td dir=\"rtl\" align=\"right\" class=\"farsi\">عشق<br /></td></tr></table><table border=\"0\" width=\"728\" cellspacing=\"1\"><tr align=\"center\" class=\"tddetail\"><td><script type=\"text/javascript\"><!--google_ad_client = \"pub-0440131808174415\"; 728x90, created 02/11/10 google_ad_slot = \"1729448038\";google_ad_width = 728;google_ad_height = 90;</script><script type=\"text/javascript\" src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\"></script></td></tr></table><div id=\"farsiwords1englishstr\"></div><div id=\"sampleofsentences\"></div></center></form>";
}
