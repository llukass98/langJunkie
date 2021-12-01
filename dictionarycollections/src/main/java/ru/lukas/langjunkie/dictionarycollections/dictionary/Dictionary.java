package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

@Getter
public abstract class Dictionary {
	protected String link;
	protected String language;
	protected String name;

	abstract public SearchResult search(String word);

	protected Document makeRequest(String url) throws IOException {
		String ref = url.split("\\?")[0];

		return constructConnection(url).referrer(ref).get();
	}

	protected Document makeRequest(String url, String payload) throws IOException {
		return constructConnection(url)
				.referrer(url)
				.requestBody(payload)
				.post();
	}

	private Connection constructConnection(String url) {
		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) "
				+"Gecko/20100101 Firefox/89.0";
		String accept = "text/html,application/xhtml+xml,application/xml;"
				+"q=0.9,image/webp,*/*;q=0.8";

		return Jsoup.connect(url)
				.userAgent(userAgent)
				.header("Accept", accept)
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Connection", "keep-alive")
				.header("Accept-Language", "en-US,en;q=0.5")
				.header("Host", url.split("//")[1].split("/")[0])
				.followRedirects(true)
				.timeout(3*1000);
	}

	protected String sanitizeInput(String input) throws IllegalArgumentException {
		input = input.trim();
		if (input.length() == 0) {
			throw new IllegalArgumentException("Searched word is an empty string");
		}
		input = input.replace("\"", "")
				.replace("'", ""); // remove quotes
		// remove unnecessary spaces if input is a phrase
		return input.replaceAll("\\s+", " ");
	}

	protected List<String> parseExamples(Document html) {
		return Collections.emptyList();
	}

	protected List<String> parseSynonyms(Document html) {
		return Collections.emptyList();
	}
}
