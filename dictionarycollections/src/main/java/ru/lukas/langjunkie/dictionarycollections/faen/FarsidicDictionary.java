package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class FarsidicDictionary extends Dictionary {

    private final Request<Document> documentRequest;

    private static final List<Character> persianLetters = List.of(
            'ء', 'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ',
            'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'ژ', 'س', 'ش',
            'ص', 'ض', 'ط', 'ظ', 'ع', 'غ', 'ف', 'ق', 'ك',
            'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ي', 'ی'
    );

    public FarsidicDictionary(Request<Document> documentRequest) {
        super(DictionaryCollection.FAEN, "farsidic", "http://www.farsidic.com/en/Lang/FaEn");
        this.documentRequest = documentRequest;

    }

    @Override
    public SearchResult search(String word) {
        word = sanitizeInput(word);
        List<String> definitions = new ArrayList<>();
        Document document = null;

        try {
            String payload = "SearchWord="
                    + URLEncoder.encode(word, StandardCharsets.UTF_8.toString())
                    + "&Criteria=Exact&ShowKeyboard=false";

            document = documentRequest.postRequest(getLink(), payload);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }

        if (document != null) {
            for (Element block : document.getElementsByClass("farsi-mean")) {
                for (String element : block.text().split(",")) {
                    String trimmed = element.trim();
                    // the word contains persian letters - cannot be a valid definition, skip it
                    if (persianLetters.contains(trimmed.charAt(0))) { continue; }
                    // add definitions ignoring duplicates
                    if (!definitions.contains(trimmed)) {
                        definitions.add(trimmed.charAt(0) == '[' ?
                                trimmed.split("]")[1].trim() :
                                trimmed);
                    }
                }
            }
        }

        return SearchResult.builder()
                .language(getLanguage())
                .name(getName())
                .link(getLink())
                .searchedWord(word)
                .results(definitions)
                .build();
    }
}
