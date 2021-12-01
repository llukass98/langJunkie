package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.net.SocketTimeoutException;
import java.util.*;

public class FarsidicsDictionary extends Dictionary {
	public FarsidicsDictionary() {
		language        = "faen";
		link            = "http://www.farsidics.com";
		name            = "farsidics";
	}

	@Override
	public SearchResult search(String word) {
		SearchResult result = new SearchResult();
		ArrayList<String> definitions = new ArrayList<>();
		String[] blockOfDefinitions;

		try {
			word = sanitizeInput(word);
			Document doc = makeRequest(link+"/ajax-searchf.php?keyword="+word);
			String text  = doc.body().text().replace(" _", "");
			List<Character> persianLetters = Arrays.asList
					('ء', 'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ',
					 'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'ژ', 'س', 'ش',
					 'ص', 'ض', 'ط', 'ظ', 'ع', 'غ', 'ف', 'ق', 'ك',
					 'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ي', 'ی');

			if (!text.contains("No Results")) {
				blockOfDefinitions = text.split(" ");

				for (int i = 1; i < blockOfDefinitions.length-1; i++) {
					// persian letters are in second word
					// break the loop, there's no definition, results array is empty
					if (persianLetters.contains(blockOfDefinitions[i].charAt(0))) {
						break;
					}
					// skip trash data, if any
					if (blockOfDefinitions[i].contains("(")) { continue; }
					// skip duplicates
					if (definitions.contains(blockOfDefinitions[i]
							.trim()
							.replace(",", ""))) { continue; }
					// find the last element and break the loop
					if (!blockOfDefinitions[i].contains(",")) {
						definitions.add(blockOfDefinitions[i].trim());
						break;
					}
					definitions.add(blockOfDefinitions[i].trim().replace(",", ""));
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
			result.setLanguage(language);
			result.setName(name);
			result.setLink(link);
			result.setSearchedWord(word);
			result.setResults(definitions);
			result.setExamples(Collections.emptyList());
			result.setSynonyms(Collections.emptyList());
		}

		return result;
	}
}