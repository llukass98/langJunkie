package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Dmitry Lukashevich
 */
@Getter
@Slf4j
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
			results = service.invokeAll(tasks).stream()
					.map(this::get)
					.filter(optional -> optional.isPresent() && !optional.get().getResults().isEmpty())
					.map(Optional::get)
					.collect(Collectors.toList());
		} catch (InterruptedException | CancellationException e) {
			log.warn(e.getMessage(), e);

			return results;
		}

		return results;
	}

	public void shutdown() { service.shutdown(); }

	private Optional<SearchResult> get(Future<SearchResult> future) {
		SearchResult result;

		try {
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			log.warn(e.getMessage(), e);

			return Optional.empty();
		}

		return Optional.of(result);
	}
}
