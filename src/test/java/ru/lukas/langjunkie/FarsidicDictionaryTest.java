package ru.lukas.langjunkie;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;

import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

@RunWith(MockitoJUnitRunner.class)
public class FarsidicDictionaryTest
{

    static Dictionary notMockedDictionary;
    static Dictionary mockedDic;
    static Document doc;
    static HashMap result;
    static String word;
                
    @BeforeClass
    public static void generalSetUp() throws Exception {
	notMockedDictionary = new FarsidicDictionary();
	doc = Jsoup.parse(html);
	word = "wonder";
	mockedDic = mock(FarsidicDictionary.class);

	doCallRealMethod().when(mockedDic).search(anyString());
	when(mockedDic.makeRequest((String) any(), anyString())).thenReturn(doc);

	result = mockedDic.search(word);
    }

    @Test
    public void makeRequestMethodShouldBeUsed() throws Exception {			

	verify(mockedDic).makeRequest((String) any(), anyString());
	
    }

    @Test
    public void searchedWordValueShouldBeCorrect() {			

       	assertThat((String) result.get("searched_word"), equalTo(word));

    }

    @Test
    public void resultsValueShouldBeArrayOfStrings() throws Exception {		
	ArrayList<String> results = (ArrayList<String>) result.get("results");
	ArrayList<String> asExpected = new ArrayList<String>();

	asExpected.add("flame");
	asExpected.add("love");
	asExpected.add("passion");
	
       	assertThat(results, is(asExpected));

    }

    @Test
    public void examplesValueShouldBeAnEmptyArray() throws Exception {		

	assertThat((ArrayList<String>) result.get("examples"), equalTo(new ArrayList<String>()));

    }

    @Test
    public void synonymsValueShouldBeAnEmptyArray() {

	assertThat((ArrayList<String>) result.get("synonyms"), equalTo(new ArrayList<String>()));

    }
    
    @Test
    public void theLanguageFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getLanguage(), equalTo("faen"));
	
    }

    @Test
    public void theLinkFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getLink(), equalTo("http://www.farsidic.com/en/Lang/FaEn"));
	
    }

    @Test
    public void theNameFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getName(), equalTo("farsidic"));
	
    }        

    static String html = "<span class=\"form-label\">Farsi Word</span></td><td class=\"input-cell k-rtl\" style=\"text-wrap: none;\"><input class=\"word-input\" dir=\"rtl\" id=\"searchBox\" name=\"SearchWord\" onkeypress=\"return ConvertKeyPress(this,event);\" style=\"width:65%;font-size:1.1em\" type=\"text\" value=\"عشق\" /><button type=\"submit\" class=\"btn btn-primary btn-my\" style=\"margin:auto 5px;\">Search</button><button type=\"button\" class=\"btn btn-warning btn-my\" onclick=\"clearAndFocus()\">Clear</button></td></tr><tr><td class=\"label-cell\"><label class=\"form-label\" for=\"exactWord\">Criteria</label></td><td class=\"input-cell\"><label class=\"control-text\"><input checked=\"checked\" data-val=\"true\" data-val-required=\"The Criteria field is required.\" id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Exact\" />Exact Word</label><label class=\"control-text\" style=\"margin: auto 25px\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Start\" />Starts With</label><label class=\"control-text\"><input id=\"Criteria\" name=\"Criteria\" type=\"radio\" value=\"Contain\" />Contains</label></td></tr></table></div><input data-val=\"true\" data-val-required=\"The ShowKeyboard field is required.\" id=\"ShowKeyboard\" name=\"ShowKeyboard\" type=\"hidden\" value=\"true\" /></form><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><!-- Content Link --><ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:15px\" data-ad-client=\"ca-pub-2654160499252485\" data-ad-slot=\"1443369263\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">flame,love,passion</span></div></div></div><div class=\"panel word-panel\"><div class=\"panel-heading\" style=\"direction:rtl\"><span class=\"english-word-single\">عشق</span></div><div class=\"panel-body\" style=\"background-color: #ECF3F7; \"><div style=\"direction:ltr;\"><span class=\"farsi-mean\">love</span></div></div></div>";
    
}
