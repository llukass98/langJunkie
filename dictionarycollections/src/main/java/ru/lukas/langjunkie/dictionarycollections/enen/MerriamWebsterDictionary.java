package ru.lukas.langjunkie.dictionarycollections.enen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.extern.slf4j.Slf4j;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class MerriamWebsterDictionary extends Dictionary {

    private final Request<String> jsonRequest;

    private static final String API_KEY = System.getenv().get("MERRIAM_WEBSTER_KEY");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Pattern cleanDefinitionPattern =
            Pattern.compile("\\{bc}|\\{sx\\||\\|+(\\w+)?}|}|\\{[a-z]_link\\||:\\ssuch\\sas|" +
                    "\\|[a-zA-Z:0-9\\s]+}|\\{dx(\\w+)?}.+\\{/dx(\\w+)?}(\\s)?|\\{(/)?it}|:\\d+");

    public MerriamWebsterDictionary(Request<String> jsonRequest) {
        super(DictionaryCollection.ENEN, "merriam-webster", "https://www.merriam-webster.com/");
        this.jsonRequest = jsonRequest;
    }

    @Override
    public SearchResult search(String word) {
        word = sanitizeInput(word);
        String response = jsonRequest.getRequest(
                "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"
                        + word + "?key=" + API_KEY);

        return SearchResult.builder()
                .language(getLanguage())
                .name(getName())
                .link(getLink())
                .searchedWord(word)
                .results(unmarshalJson(response))
                .build();
    }

    private List<String> unmarshalJson(String response) {
        if (response.equals("[]")) { return Collections.emptyList(); }

        try {
            List<String> definitions = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(response).at("/0/def");
            List<JsonNode> defs = jsonNode.findValues(("dt"));

            defs.forEach(def -> addDefinitions(def, definitions));

            return definitions;
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    private void addDefinitions(JsonNode node, List<String> definitions) {
        JsonNode defs = node.at("/0/1");

        if (defs.getNodeType().equals(JsonNodeType.STRING)) {
            definitions.add(clean(defs.asText()));
        } else if (defs.getNodeType().equals(JsonNodeType.ARRAY)) {
            defs.forEach(def -> definitions.add(clean(def.at("/0/1").asText())));
        }
    }

    private String clean(String definition) {
        return cleanDefinitionPattern.matcher(definition).replaceAll("").trim();
    }
}
