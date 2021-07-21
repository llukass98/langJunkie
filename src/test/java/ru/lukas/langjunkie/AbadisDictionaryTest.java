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
public class AbadisDictionaryTest {
    
    static Dictionary notMockedDictionary;
    static Dictionary mockedDic;
    static Document doc;
    static HashMap result;
    static String word;

    @BeforeClass
    public static void generalSetUp() throws Exception {
	notMockedDictionary = new AbadisDictionary();
	doc = Jsoup.parse(html);
	word = "wonder";
	mockedDic = mock(AbadisDictionary.class);

	doCallRealMethod().when(mockedDic).search(anyString());
	when(mockedDic.makeRequest((String) any())).thenReturn(doc);

	result = mockedDic.search(word);
    }

    @Test
    public void makeRequestMethodShouldBeUsed() throws Exception {			

	verify(mockedDic).makeRequest((String) any());
	
    }

    @Test
    public void parseSynonymsMethodShouldBeUsed() throws Exception {			

	verify(mockedDic).parseSynonyms((Document) any());
	
    }
    
    @Test
    public void parseExamplesMethodShouldBeUsed() throws Exception {			

	verify(mockedDic).parseExamples((Document) any());
	
    }

    @Test
    public void searchedWordValueShouldBeCorrect() {			

       	assertThat((String) result.get("searched_word"), equalTo(word));

    }

    @Test
    public void resultsValueShouldBeArrayOfStrings() throws Exception {		
	ArrayList<String> results = (ArrayList<String>) result.get("results");
	ArrayList<String> asExpected = new ArrayList<String>();

	asExpected.add("love");
	asExpected.add("passion");
	asExpected.add("flame");
	
       	assertThat(results, is(asExpected));

    }

    @Test
    public void examplesValueShouldBeArrayOfStrings() throws Exception {		
	String exampleStart = new StringBuilder("عشق آباد").reverse().toString();
	String exampleEnd = new StringBuilder("عشق ورزی").reverse().toString();
	String randomValue = new StringBuilder("عشق به دارایی").reverse().toString();
	ArrayList<String> examples = (ArrayList<String>) notMockedDictionary.parseExamples(doc);

	assertThat((String) examples.get(0), equalTo(exampleStart));
	assertThat((String) examples.get(examples.size()-1), equalTo(exampleEnd));
	assertThat(examples, hasItem(randomValue));	
    }

    @Test
    public void synonymsValueShouldBeArrayOfStrings() {
	String synonymStart = new StringBuilder("خاطرخواهی").reverse().toString();	
	String synonymEnd = new StringBuilder("دیوانگی").reverse().toString();
	String randomValue = new StringBuilder("احساسات تند و شدید").reverse().toString();	
	ArrayList<String> synonyms = (ArrayList<String>) notMockedDictionary.parseSynonyms(doc);
	
	assertThat((String) synonyms.get(0), equalTo(synonymStart));
	assertThat((String) synonyms.get(synonyms.size()-1), equalTo(synonymEnd));
	assertThat(synonyms, hasItem(randomValue));		
    }
    
    @Test
    public void theLanguageFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getLanguage(), equalTo("faen"));
	
    }

    @Test
    public void theLinkFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getLink(), equalTo("https://abadis.ir"));
	
    }

    @Test
    public void theNameFieldShouldBeCorrect() {
	
	assertThat(notMockedDictionary.getName(), equalTo("abadis"));
	
    }        

    static String html = "<div id=\"Means\"><aba i=\"0\" style=\"height:380px\"><div class=\"AbaRow\" style=\"height:250px\"><a href=\"http://transnet.ir/?utm_source=abadis&utm_medium=banner\" target=\"blank\" rel=\"nofollow noopener\"><img src=\"/images/ads/transnet-970-250.gif\" alt=\"ناریا نیمجرتم هکبش\" width=\"100%\" /></a></div><div class=\"AbaRow\" style=\"height:130px\"><div class=\"yn-bnr\" id=\"ynpos-6456\"></div></div></aba><div class=\"Lun\" title=\"همه\"><div class=\"MainMeanBox\"><h1>قشع</h1><hr /><table class=\"MeanAudioFa\" width=\"100%\"dir=\"ltr\"><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"love\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"love\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/l/love/\">love</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"passion\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"passion\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/p/passion/\">passion</a></div></td></tr><tr><td align=\"left\"><div class=\"Phonetic\"><a class=\"Audio\" onclick=\"PlayMp3(\"1\", \"flame\")\"><span class=\"Icons IcoAudioUs\"></span></a><a class=\"Audio\" onclick=\"PlayMp3(\"2\", \"flame\")\"><span class=\"Icons IcoAudioUk\"></span></a><a class=\"NoLinkColor\" href=\"/entofa/f/flame/\">flame</a></div></td></tr><tr></tr></table></div><div class=\"MeanMore\"><a href=\"javascript:StarWordMean()\"><span class=\"Icons IcoStar\"></span></a><div class=\"WordMsg\"></div></div></div><div class=\"Lun\" title=\"یسیلگنا هب یسراف\"><div class=\"BoxTitle\"><label>یسیلگنا هب یسراف</label></div><div class=\"BoxBody\" id=\"FaToEn\"><div class=\"WordB\">دابآ قشع</div><div class=\"Mean\">askabad or ashqabad</div><div class=\"WordB\">دازآ قشع</div><div class=\"Mean\">free love</div><div class=\"WordB\">مارتحا اب هتخیمآ قشع</div><div class=\"Mean\">adoration</div><div class=\"WordB\">زیگنا قشع</div><div class=\"Mean\">exciting love</div><div class=\"WordB\">اورپ یب ندرک یزاب قشع</div><div class=\"Mean\">swing</div><div class=\"WordB\">هناگچب قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییاراد هب قشع</div><div class=\"Mean\">avarice</div><div class=\"WordB\">ترفاسم هب قشع</div><div class=\"Mean\">wanderiust</div><div class=\"WordB\">عونمه هب قشع</div><div class=\"Mean\">caritas</div><div class=\"WordB\">نیدلاو هب قشع</div><div class=\"Mean\">piety</div><div class=\"WordB\">نتشاد قشع</div><div class=\"Mean\">care</div><div class=\"WordB\">تسود قشع</div><div class=\"Mean\">amorous</div><div class=\"WordB\">رذگدوز قشع</div><div class=\"Mean\">crush</div><div class=\"WordB\">سردوز و یحطس قشع</div><div class=\"Mean\">puppy love</div><div class=\"WordB\">ییارگ قشع</div><div class=\"Mean\">romanticism</div><div class=\"WordB\">یناوجون قشع</div><div class=\"Mean\">calf love</div><div class=\"WordB\">هقالع و قشع</div><div class=\"Mean\">fondness</div><div class=\"WordB\">ناماوت ترفن و قشع</div><div class=\"Mean\">love-hate</div><div class=\"WordB\">یزرو قشع</div>love-making<b class=\"HasMore\" title=\"قشع\" nd=\"FaToEn\" np=\"2\">...</b></div></div><aba i=\"1\" style=\"height:550px\"><div class=\"AbaRow Yek970\" style=\"height:250px\"><div class=\"yn-bnr\" id=\"ynpos-6267\"></div></div><div class=\"AbaRow YekNative\" style=\"height:300px\"><div id=\"pos-article-display-2779\"></div></div></aba><div class=\"Lun\" title=\"فدارتم\"><div class=\"BoxTitle\"><label>فدارتم</label></div><div class=\"BoxBody\" id=\"FaToEnSyn\"><div class=\"WordA\">love<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یهاوخرطاخ ،هقوشعم ،قشع ،رهم ،تبحم ،ینابرهم</div><div class=\"WordA\">amour<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">قشع</div><div class=\"WordA\">passion<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">یناسفن ضارغا ،دیدش تاساسحا ،دیدش بصعت ،دیدش و دنت تاساسحا ،یوه ،دیدش هقالع و قایتشا ،سفن یاوه ،روش ،قشع</div><div class=\"WordA\">mania<span class=\"MeanType\">(مسا)</span></div><div class=\"Mean\">دایز و لیلد یب ناجیه ،ینالقع ریغ یدنمقالع ،ییادیش ،قشع ،یگناوید</div></div></div></div>";
    
}
