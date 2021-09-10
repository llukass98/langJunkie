package ru.lukas.langjunkie.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.lukas.langjunkie.dictionarycollection.factory.CollectionFactory;
import ru.lukas.langjunkie.db.Definition;

import javax.xml.crypto.KeySelectorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RestController
public class DefinitionsController {

	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping("/api/v1.0b/definitions")
	public Definitions definitions
			(@RequestParam(name="word") String word,
			 @RequestParam(name="lang") String language,
			 @RequestParam(name="syns", defaultValue="1") String synonyms,
			 @RequestParam(name="ex", defaultValue="1") String examples)
			throws KeySelectorException
	{
		ArrayList<HashMap<String, Serializable>> definitionsFromDB =
				getDefinitionsFromDB(language, word);

		if (definitionsFromDB.isEmpty()) {
			ArrayList<HashMap<String, Serializable>> definitions;

			definitions = CollectionFactory.getCollection(language).search(word);
			for (HashMap<String, Serializable> definition : definitions) {
				saveDefinitionToDB(language, word, definition);
			}
			return new Definitions(language, word, definitions);
		}
		return new Definitions(language, word, definitionsFromDB);
	}

	private ArrayList<HashMap<String, Serializable>> getDefinitionsFromDB(String language, String word) {
		word = word.trim();
		Session session = sessionFactory.openSession();
		String query =
				"FROM Definition WHERE word='"+word+ "' AND language='" +language+ "'";
		List<Definition> queryResults = session.createQuery(query).list();
		ArrayList<HashMap<String, Serializable>> finalResult = new ArrayList<>();

		if (queryResults.isEmpty()) {
			session.close();
			return finalResult;
		}

		for (Definition result : queryResults) {
			HashMap<String, Serializable> dictionary = new HashMap<>();
			dictionary.put("name", result.getDictionary());
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
									HashMap<String, Serializable> definition)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new Definition(language, word, definition));
		session.getTransaction().commit();
		session.close();
	}
}