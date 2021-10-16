package ru.lukas.langjunkie.dictionarycollection.dictionary;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// TODO: Why is it abstract? Maybe plain class will do?
@Getter
public abstract class Collection {
	protected List<Dictionary> collection;
	protected String collectionName;

	public List<Map<String, Serializable>> search(String word) {
		List<Map<String, Serializable>> results = new ArrayList<>();
		List<Future<Map<String, Serializable>>> tasks = new ArrayList<>();

		// run all threads and save 'em in an array
		for (Dictionary dictionary : collection) {
			Callable<Map<String, Serializable>> thread = () -> dictionary.search(word);
			ExecutorService service = Executors.newSingleThreadExecutor();
			tasks.add(service.submit(thread));
		}
		// query each task if it's done and get the results
		for (Future<Map<String, Serializable>> task : tasks) {
			Map<String, Serializable> result = new HashMap<>();

			try {
				result = task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!((ArrayList<String>) result.get("results")).isEmpty()) {
				// TODO: This result.remove stuff looks weird
				result.remove("searched_word");
				result.remove("language");
				results.add(result);
			}
		}

		return results;
	}
}