package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
public abstract class Collection {
	protected List<Dictionary> collection;
	protected String collectionName;

	public List<SearchResult> search(String word) {
		List<SearchResult> results = new ArrayList<>();
		List<Future<SearchResult>> tasks = new ArrayList<>();
		// TODO: make it a worker maybe, instead of creating it every search request?
		ExecutorService service = Executors.newFixedThreadPool(10);

		// run all threads and save 'em in an array
		for (Dictionary dictionary : collection) {
			tasks.add(service.submit(() -> dictionary.search(word)));
		}

		// query each task whether it's done and get the results
		for (Future<SearchResult> task : tasks) {
			SearchResult result;

			try {
				result = task.get();

				if (!result.getResults().isEmpty()) { results.add(result); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		service.shutdown();

		return results;
	}
}