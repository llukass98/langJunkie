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

    private final List<Dictionary> dictionaries;
    private final DictionaryCollection collectionName;
    private final ExecutorService executorService;

    protected Collection(DictionaryCollection collectionName, List<Dictionary> dictionaries) {
        this.collectionName = collectionName;
        this.dictionaries = dictionaries;
        executorService = Executors.newFixedThreadPool(dictionaries.size());
    }

    public List<SearchResult> search(String word) {
        List<Callable<SearchResult>> tasks = new ArrayList<>();
        List<SearchResult> results = new ArrayList<>();

        dictionaries.forEach(dictionary -> tasks.add(() -> dictionary.search(word)));

        try {
            results = executorService.invokeAll(tasks).stream()
                    .map(this::get)
                    .filter(optional -> optional.isPresent() && !optional.get().getResults().isEmpty())
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (CancellationException e) {
            log.warn(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return results;
    }

    public void shutdown() { executorService.shutdown(); }

    private Optional<SearchResult> get(Future<SearchResult> future) {
        SearchResult result = null;

        try {
            result = future.get();
        } catch (ExecutionException e) {
            log.warn(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return Optional.ofNullable(result);
    }
}
