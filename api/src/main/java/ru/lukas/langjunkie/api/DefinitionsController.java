package ru.lukas.langjunkie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollection.factory.CollectionFactory;
import javax.xml.crypto.KeySelectorException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class DefinitionsController {     
    @GetMapping("/api/v1.0b/definitions")
    public Definitions definitions
	(@RequestParam(name="word") String word,
	 @RequestParam(name="lang") String language,
	 @RequestParam(name="syns", defaultValue="1") String synonyms,
	 @RequestParam(name="ex", defaultValue="1") String examples)
	throws KeySelectorException
    {
	ArrayList<HashMap> definitions = new ArrayList<HashMap>();
	Collection collection = CollectionFactory.getCollection(language);
	definitions = collection.search(word);


	return new Definitions(language, word, definitions);
    }
}
