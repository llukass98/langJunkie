package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.Jsoup;
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
public class BAmoozDictionaryTest {

	private Dictionary dictionary;
	private SearchResult result;
	private final String word = "wonder";

	@BeforeAll
	public void init() throws IOException {
		Request<Document> jsoupRequest = mock(JsoupRequest.class);
		Document doc = Jsoup.parse(html);
		dictionary = new BAmoozDictionary(jsoupRequest);

		when(jsoupRequest.getRequest(anyString())).thenReturn(doc);

		result = dictionary.search(word);
	}

	@Test
	public void searchedWordValueShouldBeCorrect() {
		assertThat(result.getSearchedWord(), equalTo(word));
	}

	@Test
	public void resultsValueShouldBeArrayOfStrings() throws IOException {
		List<String> results = result.getResults();
		List<String> asExpected = List.of("love", "passion");

		assertThat(results, is(asExpected));
	}

	@Test
	public void theLanguageFieldShouldBeCorrect() {
		assertThat(dictionary.getLanguage(), equalTo("faen"));
	}

	@Test
	public void theLinkFieldShouldBeCorrect() {
		assertThat(dictionary.getLink(), equalTo("https://dic.b-amooz.com"));
	}

	@Test
	public void theNameFieldShouldBeCorrect() {
		assertThat(dictionary.getName(), equalTo("b-amooz"));
	}

	static String html = "<body class=\"bg-light d-flex flex-column en\" style=\"display: none\"><!--Navbar--><div class=\"navbar-expand-md position-relative\"><nav class=\"navbar navbar-dark custom-navbar-bg px-0 pt-2\"><div class=\"container px-0\"><div class=\"row w-100 mx-0 py-1 navbar-header\"><div class=\"col-3 pl-3\"><a><div id=\"navbar-logo\"></div></a></div><div class=\"col-9 d-md-none\" align=\"right\"><a data-activates=\"slide-out\" class=\"custom-navbar-toggler button-collapse pb-0\"><span class=\"navbar-toggler-icon\"></span></a></div><div class=\"expand-md col-9 p-0 rtl\"><button class=\"navbar-toggler color-white\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarLinks\" aria-controls=\"navbarLinks\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"><span class=\"sr-only\">Toggle drawer</span><i class=\"material-icons\">menu</i></button><div class=\"collapse navbar-collapse pt-2 rtl\" id=\"navbarLinks\"><ul class=\"navbar-nav pr-3 mdc-typography--body1\"><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0\"><a class=\"color-white\" href=\"https://dic.b-amooz.com\">خانه</a></li><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0 d-md-none\"><div><a class=\"color-white\" data-toggle=\"collapse\" href=\"#collapseDictionaries\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseDictionaries\">انتخاب دیکشنری<span class=\"fa fa-caret-down\"></span></a><div class=\"pr-3 collapse\" id=\"collapseDictionaries\"><a class=\"color-white w-100\" href=\"https://dic.b-amooz.com/en/dictionary\"><span class=\"mdc-list-item__text\">انگلیسی به فارسی</span></a><a class=\"color-white w-100\" href=\"https://dic.b-amooz.com/de/dictionary\"><span class=\"mdc-list-item__text\">آلمانی به فارسی</span></a><a class=\"color-white w-100\" href=\"https://dic.b-amooz.com/fr/dictionary\"><span class=\"mdc-list-item__text\">فرانسه به فارسی</span></a><a class=\"color-white w-100\" href=\"https://dic.b-amooz.com/tr/dictionary\"><span class=\"mdc-list-item__text\">ترکی استانبولی به فارسی</span></a></div></div></li><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0 d-none d-md-inline\"><div class=\"dropdown\"><a class=\"color-white dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">انتخاب دیکشنری</a><div class=\"dropdown-menu rtl\" aria-labelledby=\"navbarDropdown\"><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/en/dictionary\"><span class=\"mdc-list-item__text\">انگلیسی به فارسی</span></a><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/de/dictionary\"><span class=\"mdc-list-item__text\">آلمانی به فارسی</span></a><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/fr/dictionary\"><span class=\"mdc-list-item__text\">فرانسه به فارسی</span></a><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/tr/dictionary\"><span class=\"mdc-list-item__text\">ترکی استانبولی به فارسی</span></a><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/ar/dictionary\"><span class=\"mdc-list-item__text\">عربی به فارسی</span></a><a class=\"dropdown-item\" href=\"https://dic.b-amooz.com/es/dictionary\"><span class=\"mdc-list-item__text\">اسپانیایی به فارسی</span></a></div></div></li><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0\"><a class=\"color-white\" href=\"https://dic.b-amooz.com/en/dictionary/conjugation\">صرف فعل</a></li><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0\"><a class=\"color-white\" href=\"https://lang.b-amooz.com\">وبسایت آموزشی</a></li><li class=\"nav-item pl-lg-5 pl-md-3 mb-3 mb-md-0\"><a class=\"color-white\" href=\"https://dic.b-amooz.com/about\">درباره ما</a></li><li class=\"nav-item pl-lg-5 pl-md-3\"><a class=\"color-white\" href=\"https://dic.b-amooz.com/contact\">تماس با ما</a></li></ul></div></div></div></div><div class=\"pb-1 w-100\"><script></script><div class=\"container px-0\"><div class=\"input-group col-10 offset-1 px-3 navbar-header position-relative my-2\"><input type=\"text\" class=\"search-input form-control d-inline-block rounded-pill\" id=\"search-input\" placeholder=\" جستجوی واژگان فارسی یا انگلیسی\" value=\"عشق\" /><i class=\"fa fa-search search-icon\" id=\"search-icon\"></i></div></div><div class=\"container px-0 rtl\"><div class=\"px-0 quick-access-parent mb-md-3 mb-1\" align=\"right\"><div class=\"custom-right-mask custom-mask\"></div><div class=\"mt-1 overflow-auto text-nowrap pt-1 rtl\" id=\"quick-access\"><span class=\"mx-1\"></span><a class=\"quick-access-items\" href=\"#Noun_love_1\"><span class=\"chip m-0 d-inline-block rtl\">1.love</span></a><a class=\"quick-access-items\" href=\"#Noun_passion_2\"><span class=\"chip m-0 d-inline-block rtl\">2.passion</span></a><span class=\"mx-1\"></span></div><div class=\"custom-left-mask custom-mask\"></div></div></div></div></nav><div class=\"en-navbar-bg navbar-language-bar\"></div><div id=\"slide-out\" class=\"side-nav fixed d-md-none navbar-bg-wp\"><ul class=\"custom-scrollbar\"><li><div class=\"logo-wrapper waves-light border-0 mb-1\" align=\"center\" id=\"sidebar-logo\"><a href=\"#\"><img src=\"https://dic.b-amooz.com/img/b-amooz-logo.png\" /></a></div></li><li><ul class=\"social\"><li><a href=\"https://www.facebook.com/beamooz/\" target=\"_blank\"><i class=\"sidebar-social fa fa-facebook-f\"></i></a></li><li><a href=\"https://www.instagram.com/b.amooz/\" target=\"_blank\"><i class=\"sidebar-social fa fa-instagram\"></i></a></li><li><a href=\"http://telegram.me/beamooz\" target=\"_blank\"><i class=\"sidebar-social fa fa-telegram\"></i></a></li><li><a href=\"https://twitter.com/b_amooz\" target=\"_blank\"><i class=\"sidebar-social fa fa-twitter\"></i></a></li></ul></li><li><ul class=\"collapsible collapsible-accordion rtl mt-0\"><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect py-1\" href=\"https://dic.b-amooz.com\"><span class=\"mr-3\">خانه</span></a></li><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect arrow-r py-1\"><span class=\"mr-3\">انتخاب دیکشنری</span><i class=\"fa fa-angle-down rotate-icon py-1\" id=\"custom-rotate-icon\"></i></a><div class=\"collapsible-body\"><ul><li><a href=\"https://dic.b-amooz.com/en/dictionary\" class=\"waves-effect px-4\"><div id=\"sidebar-dict-en\"></div><span class=\"mr-2\">انگلیسی به فارسی</span></a></li><li><a href=\"https://dic.b-amooz.com/de/dictionary\" class=\"waves-effect px-4\"><div id=\"sidebar-dict-de\"></div><span class=\"mr-2\">آلمانی به فارسی</span></a></li><li><a href=\"https://dic.b-amooz.com/fr/dictionary\" class=\"waves-effect px-4\"><div id=\"sidebar-dict-fr\"></div><span class=\"mr-2\">فرانسه به فارسی</span></a></li><li><a href=\"https://dic.b-amooz.com/tr/dictionary\" class=\"waves-effect px-4\"><div id=\"sidebar-dict-tr\"></div><span class=\"mr-2\">ترکی استانبولی به فارسی</span></a></li></ul></div></li><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect py-1\" href=\"https://dic.b-amooz.com/en/dictionary/conjugation\"><span class=\"mr-3\">صرف فعل</span></a></li><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect py-1\" href=\"https://lang.b-amooz.com\"><span class=\"mr-3\">وبسایت آموزشی</span></a></li><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect py-1\" href=\"https://dic.b-amooz.com/about\"><span class=\"mr-3\">درباره ما</span></a></li><li class=\"sidebar-item\"><a class=\"collapsible-header waves-effect py-1\" href=\"https://dic.b-amooz.com/contact\"><span class=\"mr-3\">تماس با ما</span></a></li></ul></li></ul><div class=\"sidenav-bg en-navbar-bg\"></div></div></div><div class=\"container bg-light mt-2\" id=\"main-content\"><div class=\"w-100 my-auto\"><div class=\"word-row pos-noun-bg\"><div class=\"word-row-side\"><div class=\"row py-4\"><div class=\"col-md-12\"><span class=\"small mdc-typography--headline6 text-muted mt-1 d-inline-block\">[اسم]</span><h1 class=\"mdc-typography--headline4 ltr d-inline pull-right\">عشق</h1><span class=\"mr-2 text-muted mdc-typography--headline4 pull-right font-size-120\">( ع-ش-ق )</span></div><div class=\"col-md-12 text-right text-muted font-weight-bold \"></div></div></div></div><div class=\"word-row pos-noun-bg\"><div class=\"word-row-side\"><div class=\"dropdown-divider m-0 p-0\"></div><div class=\"row\"><div class=\"col-12 py-4 position-relative px-amp-15\"><div class=\"mb-2 \"><div class=\"row px-0 mdc-typography--headline5 mt-0 mb-3\"><div class=\"w-100\"><span class=\"color-white reverse-translation-index reverse-pos-noun-bg\" style=\"height: 30px\">1</span><a class=\"anchors\" name=\"Noun_love_1\"></a><span class=\" font-weight-bold\">love</span></div><span class=\"w-100 text-right rtl text-muted h6\"></span></div></div><div class=\"translation-box mt-5\"><div class=\"p-2 border rgba-blue-grey-slight\"><div class=\"mb-2\"><a name=\"Noun_link_\"><p class=\"mb-0\"><a target=\"_blank\" href=\"https://dic.b-amooz.com/en/dictionary/w?word=love\">love</a><small class=\"text-muted\">/lʌv/</small><small class=\"text-muted ml-2\">/lʌv/</small></p><p class=\"reverse-word-translation-desc\"></p></a></div></div></div></div></div></div></div><div class=\"word-row pos-noun-bg\"><div class=\"word-row-side\"><div class=\"dropdown-divider m-0 p-0\"></div><div class=\"row\"><div class=\"col-12 py-4 position-relative px-amp-15\"><div class=\"mb-2 \"><div class=\"row px-0 mdc-typography--headline5 mt-0 mb-3\"><div class=\"w-100\"><span class=\"color-white reverse-translation-index reverse-pos-noun-bg\" style=\"height: 30px\">2</span><a class=\"anchors\" name=\"Noun_passion_2\"></a><span class=\" font-weight-bold\">passion</span></div><span class=\"w-100 text-right rtl text-muted h6\"></span></div></div><div class=\"translation-box mt-5\"><div class=\"p-2 border rgba-blue-grey-slight\"><div class=\"mb-2\"><a name=\"Noun_link_\"><p class=\"mb-0\"><a target=\"_blank\" href=\"https://dic.b-amooz.com/en/dictionary/w?word=passion\">passion</a><small class=\"text-muted\">/ˈpæʃən/</small><small class=\"text-muted ml-2\">/ˈpæʃən/</small></p><p class=\"reverse-word-translation-desc\"></p></a></div></div></div></div></div></div></div><div class=\"clearfix my-1\"></div></div></div></body>";
}
