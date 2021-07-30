package ru.lukas.langjunkie.dictionarycollection.faen;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;
import java.nio.charset.StandardCharsets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;

public class FarsidicDictionary extends Dictionary {
    public FarsidicDictionary() {
	language       = "faen";
	link           = "http://www.farsidic.com/en/Lang/FaEn";
	dictionaryName = "farsidic";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
	List<Character> persianLetters = Arrays.asList
	    ('ء', 'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ',
	     'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'ژ', 'س', 'ش',
	     'ص', 'ض', 'ط', 'ظ', 'ع', 'غ', 'ف', 'ق', 'ك',
	     'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ي', 'ی');	

	try {
	    word = sanitizeInput(word);
	    String payload = "SearchWord="
		+URLEncoder.encode(word, StandardCharsets.UTF_8.toString())
		+"&Criteria=Exact&ShowKeyboard=false";
	    
	    Document doc = makeRequest(link, payload);
	    // get definitions
	    for (Element block : doc.getElementsByClass("farsi-mean")) {
		for (String element : block.text().split("\\,")) {
		    String trimmed = element.trim();
		    // the word contains persian letters
		    // that cannot be a valid definition, skip it
		    if (persianLetters.contains(trimmed.charAt(0))) { continue; }
		    // otherwise, continue as usual
		    if (!definitions.contains(trimmed)) {
			definitions.add(trimmed.charAt(0) == '[' ?
					trimmed.split("]")[1].trim() :
					trimmed);
		    }
		}
	    }
	} catch (SocketTimeoutException | HttpStatusException ste) {
	    // TODO: log the exception	    
	} catch (IllegalArgumentException iae) {
	    // TODO: log the exception
	} catch (NullPointerException npe) {
	    // TODO: log the exception	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    result.put("language",      language);
	    result.put("name",          dictionaryName);
	    result.put("link",          link);
	    result.put("searched_word", word);
	    result.put("results",       definitions);
	    result.put("examples",      new ArrayList<String>());
	    result.put("synonyms",      new ArrayList<String>());	    	    

	    return result;
	}
    }    
}
