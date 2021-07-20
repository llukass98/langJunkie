package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;

class FarsidicsDictionary extends Dictionary {

    private static FarsidicsDictionary instance;

    private FarsidicsDictionary() {
	language = "faen";
	link = "http://www.farsidics.com";
	dictionaryName = "farsidics";
    }

    public static FarsidicsDictionary getInstance() {
	if (instance == null) {
	    instance = new FarsidicsDictionary();
	}

	return instance;
    }

    public LinkedHashMap search(String word) {
	LinkedHashMap result = new LinkedHashMap();
	ArrayList<String> resultSet = new ArrayList<String>();
	String text;
        String defs[] = new String[10];

	result.put("language", language);
	result.put("name", dictionaryName);
	result.put("link", link);
	result.put("searched_word", word);

	try {
	    Document doc = makeRequest(link+"/ajax-searchf.php?keyword="+word);

	    text = doc.body().text();

	    if (!text.contains("No Results")) {
		defs = text.split("\\,");
		
		resultSet.add(defs[0].trim().split(" ")[1]);
		
		for (int i = 1; i < defs.length-1; i++) {
		    if (defs[i].trim().contains(" ")) {
			resultSet.add(defs[i].trim().split(" ")[0]);			
			break;
		    }

		    resultSet.add(defs[i].trim());			    		    
		}
	    }

	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception	    
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    result.put("results", resultSet);
	    result.put("examples", new ArrayList<String>());
	    result.put("synonyms", new ArrayList<String>());
	    
	    return result;
	}
    }
    
}
