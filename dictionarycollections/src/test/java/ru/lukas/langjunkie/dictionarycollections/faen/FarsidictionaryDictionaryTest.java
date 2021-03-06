package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.nodes.Document;
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

import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;

import ru.lukas.langjunkie.dictionarycollections.AbstractDictionaryTest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

/**
 * @author Dmitry Lukashevich
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("FarsidictionaryDictionary tests")
class FarsidictionaryDictionaryTest extends AbstractDictionaryTest {

    private final static String HTML = "<body><div id=\"farsidictionary\"><div id=\"header_top\"><h3>Farsi Dictionary</h3><h3>Farsi - English Dictionary</h3><h3>Persian Dictionary</h3><h3>Farsi English Translation</h3><div class=\"addthis_toolbox addthis_default_style\" id=\"divshare\"><a href=\"https://www.addthis.com/bookmark.php?v=250&amp;username=sozluknet\" class=\"addthis_button_compact\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\">Share</a><span class=\"addthis_separator\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\">|</span><a class=\"addthis_button_facebook\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\"></a><a class=\"addthis_button_myspace\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\"></a><a class=\"addthis_button_google\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\"></a><a class=\"addthis_button_twitter\" addthis:url=\"https://www.farsidictionary.net\" addthis:title=\"Farsi Dictionary\" addthis:description=\"Farsi - English Dictionary & Translation\"></a></div><script type=\"text/javascript\" src=\"https://s7.addthis.com/js/250/addthis_widget.js#username=sozluknet\"></script></div><div id=\"header\"><div id=\"logo\"><a href=\"https://www.farsidictionary.net/\"><img src=\"/images/logo.gif\" alt=\"Farsi Dictionary\" title=\"Farsi Dictionary\" width=\"240\" height=\"75\" border=\"0\" /></a></div><div id=\"ads\"><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><!-- 728x90 farsidictionary.net --><ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:90px\" data-ad-client=\"ca-pub-6155275322913172\" data-ad-slot=\"1045610405\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script></div></div><div id=\"tmenu\"><div id=\"canvas\"><ul><li class=\"active\"><a href=\"https://www.farsidictionary.net/\">Dictionary</a></li><li><a href=\"/translation.html\">Text Translation</a></li><li><a href=\"/tools.html\">Tools</a></li><li><a href=\"/resources.html\">Resources</a></li></ul><ul id=\"right\"><li><a href=\"/contact-us.html\">Contact Us</a></li><li><a href=\"/help.html\">Help</a></li></ul></div></div><div id=\"container\"><div id=\"lcol\"><div id=\"sbart\"><ul><li class=\"active\"><a href=\"http://www.farsidictionary.net/\">Farsi - English Dictionary</a></li><li><a href=\"/translation.html\">Farsi - English Translation</a></li></ul></div><div id=\"sbar\"><form method=\"get\" action=\"index.php\" name=\"SForm\" id=\"searchform\" onsubmit=\"return check('You must enter a word!')\"><div id=\"search_text\">Word</div><div id=\"search_box\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td><input type=\"text\" name=\"q\" value=\"??????\" class=\"keyboardInput\" id=\"search_input\" style=\"vertical-align:middle;\" /></td></tr></table></div><div class=\"clear\"></div><div id=\"search_translate\"><input type=\"submit\" value=\"Translate\" id=\"translate\" /><iframe src=\"http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fwww.farsidictionary.net&amp;layout=button_count&amp;show_faces=true&amp;width=100&amp;action=like&amp;font&amp;colorscheme=light&amp;height=21\" scrolling=\"no\" frameborder=\"0\" style=\"border:none; overflow:hidden; width:100px; height:21px;\" allowtransparency=\"true\"></iframe><!-- Place this tag in your head or just before your close body tag --><script type=\"text/javascript\" src=\"https://apis.google.com/js/plusone.js\"></script><!-- Place this tag where you want the +1 button to render --><g:plusone size=\"medium\"></g:plusone></div><script type=\"text/javascript\">document.forms.SForm.q.focus();document.forms.SForm.q.select();</script></form></div><!-- div# sbar --><div class=\"clear\"></div><div align=\"center\" style=\"margin-top:10px;\"></div></div><div id=\"rcol\"></div><div class=\"clear\"></div><div align=\"definition\"><div align=\"center\"><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><table border=\"0\" width=\"100%\" style=\"font-family:Verdana;font-size:13px;\" cellpadding=\"4\" cellspacing=\"0\" name=\"faen\" id=\"faen\"><tr><td align=\"left\" bgcolor=\"#A6C9F0\"><b><img src=\"images/fa.gif\" border=\"0\" width=\"16\" height=\"11\" />Farsi ?? English<img src=\"images/en.gif\" border=\"0\" width=\"16\" height=\"11\" />Translations for \"??????\"</b></td></tr><tr><td width=\"100%\" bgcolor=\"#F5F5F5\"><table border=\"0\" width=\"100%\" style=\"font-family:Verdana;font-size:13px;\" cellspacing=\"0\" cellpadding=\"3\"><tr><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" width=\"40\" bgcolor=\"#F5F5F5\" align=\"center\"><span title=\"Function\"><b>Func.</b></span></td><td style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#F5F5F5\" width=\"140\" align=\"right\" dir=\"rtl\"><b>Word</b></td><td style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#F5F5F5\" align=\"left\"><b>Definition</b></td><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" width=\"100\" bgcolor=\"#F5F5F5\" align=\"center\"><span title=\"Pronunciation\"><b>Pron.</b></span></td></tr><tr><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"><span title=\"noun\">N</span></td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;font-size:17px;\" bgcolor=\"#FFFFFF\" width=\"140\" align=\"right\" dir=\"rtl\"><a href=\"?q=??????\">??????</a></td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#FFFFFF\" align=\"left\">love</td><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"></td></tr><tr><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"><span title=\"noun\">N</span></td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;font-size:17px;\" bgcolor=\"#FFFFFF\" width=\"140\" align=\"right\" dir=\"rtl\">??????</td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#FFFFFF\" align=\"left\">amour</td><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"></td></tr><tr><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"><span title=\"noun\">N</span></td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;font-size:17px;\" bgcolor=\"#FFFFFF\" width=\"140\" align=\"right\" dir=\"rtl\">??????</td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#FFFFFF\" align=\"left\">passion</td><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"></td></tr><tr><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"><span title=\"noun\">N</span></td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;font-size:17px;\" bgcolor=\"#FFFFFF\" width=\"140\" align=\"right\" dir=\"rtl\">??????</td><td valign=\"top\" style=\"border-bottom:1px solid #C0C0C0;\" bgcolor=\"#FFFFFF\" align=\"left\">mania</td><td style=\"border-bottom:1px solid #C0C0C0;font-size:11px;\" bgcolor=\"#FFFFFF\" align=\"center\"></td></tr></table></td></tr></table><br /></td></tr></table></div></div></div><div id=\"footer\"><ul><li><a href=\"/privacy.html\">Privacy Policy</a></li><li><a href=\"/terms-of-use.html\">Terms of Use</a></li><li><a href=\"/contact-us.html\">Contact Us</a></li></ul>??<a href=\"https://www.farsidictionary.net\" title=\"Farsi Dictionary\">Farsi Dictionary</a></div></div></body>";

    @BeforeAll
    public void setUp() {
        dictionary = new FarsidictionaryDictionary(jsoupRequest);

        when(jsoupRequest.getRequest(anyString())).thenReturn(Jsoup.parse(HTML));

        result = dictionary.search(word);
    }

    @Test
    @DisplayName("getSearchedWord() should return correct value")
    void searchedWordValueShouldBeCorrect() {
        assertThat(result.getSearchedWord(), equalTo(word));
    }

    @Test
    @DisplayName("getLanguage() should return correct language")
    void theLanguageFieldShouldBeCorrect() {
        assertThat(result.getLanguage(), equalTo(DictionaryCollection.FAEN));
    }

    @Test
    @DisplayName("getLink() should return correct link")
    void theLinkFieldShouldBeCorrect() {
        assertThat(result.getLink(), equalTo("https://www.farsidictionary.net"));
    }

    @Test
    @DisplayName("getName() should return correct name")
    void theNameFieldShouldBeCorrect() {
        assertThat(result.getName(), equalTo("farsidictionary"));
    }

    @Test
    @DisplayName("getResults() should return expected list of results")
    void resultsValueShouldBeArrayOfStrings() {
        List<String> asExpected = List.of("love", "amour", "passion", "mania");

        assertThat(result.getResults(), is(asExpected));
    }

    @ParameterizedTest(name = "when input is {0} getSearchedWord() should return {1}")
    @DisplayName("searched word should be clear of any non-letter character")
    @CsvSource(delimiter = '|', value = {
            "!?.,word.,!?|word", "\"word\"|word", "\\'word\\'|word", "??(word)??|word", "[{??word??}]|word"
    })
    void searchedWordShouldBeSanitised(String input, String asExpected) {
        SearchResult result = dictionary.search(input);

        assertThat(result.getSearchedWord(), is(asExpected));
    }

    @Test
    @DisplayName("throws an IllegalArgumentException when searched word is an empty string")
    void throwsIllegalArgumentExceptionWhenSearchedWordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> dictionary.search(""));
    }

    @Test
    @DisplayName("returns and empty list of results when empty document is returned")
    void returnsAnEmptyResultWhenIOExceptionIsThrownInJsoupRequest() {
        when(jsoupRequest.getRequest(anyString())).thenReturn(new Document(""));

        assertThat(dictionary.search(word).getResults(), is(Collections.emptyList()));
    }
}
