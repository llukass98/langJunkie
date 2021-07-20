package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Attributes;
import org.jsoup.select.Elements;

class DictionaryFarsiDictionary extends Dictionary {

    private static DictionaryFarsiDictionary instance;

    private DictionaryFarsiDictionary() {
	language = "faen";
	link = "http://www.dictionary-farsi.com";
	dictionaryName = "dictionary-farsi";
    }

    public static DictionaryFarsiDictionary getInstance() {
	if (instance == null) {
	    instance = new DictionaryFarsiDictionary();
	}

	return instance;
    }

    public LinkedHashMap search(String word) {
	Elements elems;
	LinkedHashMap result = new LinkedHashMap();
	ArrayList<String> resultSet = new ArrayList<String>();
	ArrayList<String> examplesSet = new ArrayList<String>();	

	result.put("language", language);
	result.put("name", dictionaryName);
	result.put("link", link);
	result.put("searched_word", word);
	    
	try {
	    String payload = "submith=ok&text1="
		+URLEncoder.encode(word, "windows-1256")
		+"&submit1=Search&r1=p2e";

	    String queryString;
	    Document doc = makeRequest(link, payload);

	    elems = doc.getElementsByAttribute("onmouseover");

	    // get definitions
	    for (Element element : elems) {		
		resultSet.add(element.text().trim());
	    }

	    // get examples
	    /*queryString = doc.getElementsByAttributeValueContaining("src", "dic.asp?a=")
		.first()
		.attributes()
		.get("src");
	    
	    doc = makeRequest(link+"/"+queryString);
	    elems = doc.getElementById("farsiwordslenglishstr").getElementsByClass("tddetail");

	    for (Element elem : elems) {
		System.out.println(elem.text().trim());
		}*/
			      
	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    result.put("results", resultSet);
	    result.put("examples", examplesSet);
	    result.put("synonyms", new ArrayList<String>());

	    return result;
	}
    }
    
}
