package ru.lukas.langjunkie.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lukas.langjunkie.db.Definition;
import ru.lukas.langjunkie.dictionarycollection.factory.CollectionFactory;

import javax.xml.crypto.KeySelectorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1.0b")
@RestController
public class DefinitionsController {

	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping("/definitions")
	public Definitions definitions
			(@RequestParam(name="word") String word,
			 @RequestParam(name="lang") String language,
			 @RequestParam(name="syns", defaultValue="1") String synonyms,
			 @RequestParam(name="ex", defaultValue="1") String examples)
			throws KeySelectorException
	{
		List<Map<String, Serializable>> definitionsFromDB =
				getDefinitionsFromDB(language, word);

		if (definitionsFromDB.isEmpty()) {
			List<Map<String, Serializable>> definitions;

			definitions = CollectionFactory.getCollection(language).search(word);
			for (Map<String, Serializable> definition : definitions) {
				saveDefinitionToDB(language, word, definition);
			}
			return new Definitions(200, language, word, definitions);
		}
		return new Definitions(200, language, word, definitionsFromDB);
	}

	private List<Map<String, Serializable>> getDefinitionsFromDB(String language, String word) {
		word = word.trim();
		Session session = sessionFactory.openSession();
		String query =
				"FROM Definition WHERE word='"+word+ "' AND language='" +language+ "'";
		List<Definition> queryResults = session.createQuery(query).list();
		List<Map<String, Serializable>> finalResult = new ArrayList<>();

		if (queryResults.isEmpty()) {
			session.close();
			return finalResult;
		}

		for (Definition result : queryResults) {
			Map<String, Serializable> dictionary = new HashMap<>();
			dictionary.put("name", result.getName());
			dictionary.put("link", result.getLink());
			dictionary.put("results", result.getDefinitions());
			dictionary.put("synonyms", result.getSynonyms());
			dictionary.put("examples", result.getExamples());
			finalResult.add(dictionary);
		}

		session.close();
		return finalResult;
	}

	private void saveDefinitionToDB(String language,
									String word,
									Map<String, Serializable> definition)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new Definition(language, word, definition));
		session.getTransaction().commit();
		session.close();
	}
}