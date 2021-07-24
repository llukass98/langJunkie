package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;

class FarsidicsDictionary extends Dictionary {
    public FarsidicsDictionary() {
	language        = "faen";
	link            = "http://www.farsidics.com";
	dictionaryName  = "farsidics";
    }

    public HashMap search(String word) {
	HashMap result                = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
        String blockOfDefinitions[]   = new String[50];

	try {
	    if (word.trim().length() > 0) {
		Document doc = makeRequest(link+"/ajax-searchf.php?keyword="+word);
		String text  = doc.body().text();

		if (!text.contains("No Results")) {
		    blockOfDefinitions = text.split("\\,");
		    definitions.add(blockOfDefinitions[0].trim().split(" ")[1]);
		    
		    for (int i = 1; i < blockOfDefinitions.length-1; i++) {
			if (blockOfDefinitions[i].trim().contains(" ")) {
			    definitions.add(blockOfDefinitions[i].trim().split(" ")[0]);			
			    break;
			}

			definitions.add(blockOfDefinitions[i].trim());			    		    
		    }
		}
	    }
	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception	    
	} catch (IOException e) {
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
