package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Dmitry Lukashevich
 */
@Getter
public abstract class Collection {

	private final List<Dictionary> collection;
	private final String collectionName;
	private final ExecutorService service;

	public Collection(String collectionName, List<Dictionary> collection) {
		this.collectionName = collectionName;
		this.collection = collection;
		service = Executors.newFixedThreadPool(10);
	}

	public List<SearchResult> search(String word) {
		List<SearchResult> results = new ArrayList<>();
		List<Future<SearchResult>> tasks = new ArrayList<>();

		for (Dictionary dictionary : collection) {
			tasks.add(service.submit(() -> dictionary.search(word)));
		}

		for (Future<SearchResult> task : tasks) {
			SearchResult result;

			try {
				result = task.get();

				if (!result.getResults().isEmpty()) { results.add(result); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return results;
	}

	public void shutdown() {
		service.shutdown();
	}
}
