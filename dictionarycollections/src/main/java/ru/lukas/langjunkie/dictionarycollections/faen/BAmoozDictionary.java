package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class BAmoozDictionary extends Dictionary {

    private final Request<Document> documentRequest;

    public BAmoozDictionary(Request<Document> documentRequest) {
        super(DictionaryCollection.FAEN, "b-amooz", "https://dic.b-amooz.com");
        this.documentRequest = documentRequest;
    }

    @Override
    public SearchResult search(String word) {
        word = sanitizeInput(word);
        String requestUrl = getLink() + "/en/dictionary/rw?word=" + word;
        Document document = documentRequest.getRequest(requestUrl);

        return SearchResult.builder()
                .language(getLanguage())
                .name(getName())
                .link(getLink())
                .searchedWord(word)
                .results(parseDefinitions(document))
                .build();
    }

    private List<String> parseDefinitions(Document document) {
        List<String> definitions = new ArrayList<>();

        if (document != null) {
            for (Element element : document.getElementsByClass("word-row-side")) {
                // if non-persian word has been searched return an empty List
                if (!isPersianWord(document)) { return Collections.emptyList(); }
                // skip first element
                if (element.child(0).hasClass("py-4")) { continue; }

                for (Element span : element.getElementsByTag("span")) {
                    // skip numbers, commas, spaces and other trash
                    if (span.hasClass("reverse-translation-index")
                            || span.hasClass("ml-n2")
                            || span.hasClass("text-muted")) { continue; }

                    definitions.add(span.text().trim());
                }
            }
        }

        return definitions;
    }

    private boolean isPersianWord(Document document) {
        Elements elements = document.getElementsByClass("reverse-word-translation-desc");

        return elements.first() != null;
    }
}
