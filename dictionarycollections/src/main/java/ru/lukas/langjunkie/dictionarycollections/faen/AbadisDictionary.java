package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.util.ArrayList;
import java.util.List;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class AbadisDictionary extends Dictionary {

    private final Request<Document> documentRequest;

    public AbadisDictionary(Request<Document> documentRequest) {
        super(DictionaryCollection.FAEN, "abadis", "https://abadis.ir");
        this.documentRequest = documentRequest;
    }

    @Override
    public SearchResult search(String word) {
        word = sanitizeInput(word);
        List<String> definitions = new ArrayList<>();
        String requestUrl = getLink() + "/?lntype=fatoen&word=" + word;
        Document document = documentRequest.getRequest(requestUrl);
        Element rawDefinitions = null;

        if (document != null) { rawDefinitions = document.getElementById("Means"); }
        if (rawDefinitions != null) {
            for (Element element:rawDefinitions.getElementsByClass("NoLinkColor")) {
                String definition = element.text();

                definitions.add(definition.charAt(0) == '[' ?
                        definition.split("]")[1].trim() :
                        definition.trim());
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
