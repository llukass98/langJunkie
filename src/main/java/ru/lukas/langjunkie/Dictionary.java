package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;
import java.io.IOException;
    
public abstract class Dictionary {
    protected String link;
    protected String language;
    protected String dictionaryName;
    
    abstract public HashMap search(String word);
    
    public String getLanguage() {
	return language;
    }

    public String getLink() {
	return link;
    }

    public String getName() {
	return dictionaryName;
    }    

    protected Document makeRequest(String url)
	throws SocketTimeoutException, HttpStatusException, IOException
    {
	String ref = url.split("\\?")[0];

	return constructConnection(url).referrer(ref).get();
    }
    
    protected Document makeRequest(String url, String payload)
	throws SocketTimeoutException, HttpStatusException, IOException
    {
	return constructConnection(url)
	    .referrer(url)
	    .requestBody(payload)
	    .post();
    }

    private Connection constructConnection(String url) {
	return Jsoup.connect(url)
	    .userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0")
	    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
	    .header("Accept-Encoding", "gzip, deflate, br")
	    .header("Connection", "keep-alive")
	    .header("Accept-Language", "en-US,en;q=0.5")
	    .header("Host", url.split("//")[1].split("/")[0])
	    .followRedirects(true)
	    .timeout(4*1000);  	
    }

    protected ArrayList<String> parseExamples(Document html) {
	return new ArrayList<String>();
    }

    protected ArrayList<String> parseSynonyms(Document html) {
	return new ArrayList<String>();
    }

    protected String sanitizeInput(String input) throws IllegalArgumentException {
	input = input.trim(); // trim spaces
	if (input.length() == 0) {
	    throw new IllegalArgumentException("Searched word is an empty string");
	}
	input = input.replace("\"", "").replace("'", ""); // remove quotes

	return input;
    }
}
