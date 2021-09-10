package ru.lukas.langjunkie.dictionarycollection.dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class Collection {
	protected ArrayList<Dictionary> collection;
	protected String collectionName;

	public ArrayList<Dictionary> getDictionaries() {
		return collection;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public ArrayList<HashMap<String, Serializable>> search(String word) {
		ArrayList<HashMap<String, Serializable>> results = new ArrayList<>();
		ArrayList<Future<HashMap<String, Serializable>>> tasks = new ArrayList<>();

		// run all threads and save 'em in an array
		for (Dictionary dictionary : collection) {
			Callable<HashMap<String, Serializable>> thread = () -> dictionary.search(word);
			ExecutorService service = Executors.newSingleThreadExecutor();
			tasks.add(service.submit(thread));
		}
		// query each task if it's done and get the results
		for (Future<HashMap<String, Serializable>> task : tasks) {
			HashMap<String, Serializable> result = new HashMap<>();

			try {
				result = task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!((ArrayList<String>) result.get("results")).isEmpty()) {
				result.remove("searched_word");
				result.remove("language");
				results.add(result);
			}
		}

		return results;
	}
}
