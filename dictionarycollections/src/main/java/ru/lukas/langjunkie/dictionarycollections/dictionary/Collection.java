package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
		List<Callable<SearchResult>> tasks = new ArrayList<>();
		List<SearchResult> results = new ArrayList<>();

		collection.forEach(dictionary -> tasks.add(() -> dictionary.search(word)));
		try {
			results = service.invokeAll(tasks, 5, TimeUnit.SECONDS).stream()
					.map(this::get)
					.filter(result -> !result.getResults().isEmpty())
					.collect(Collectors.toList());
		} catch (InterruptedException | CancellationException e) {
			e.printStackTrace(); // TODO: add logger
			return results;
		}

		return results;
	}

	public void shutdown() { service.shutdown(); }

	private SearchResult get(Future<SearchResult> future) {
		SearchResult result = new SearchResult();

		try {
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace(); //TODO: add logger
			return result;
		}

		return result;
	}
}
