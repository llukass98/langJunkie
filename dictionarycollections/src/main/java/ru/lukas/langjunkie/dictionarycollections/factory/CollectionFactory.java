package ru.lukas.langjunkie.dictionarycollections.factory;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.faen.FaEnCollection;

import javax.xml.crypto.KeySelectorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Dmitry Lukashevich
 */
public class CollectionFactory {

	private final Map<DictionaryCollection, Supplier<Collection>> collections;
	private final Map<DictionaryCollection, Collection> cache;

	public CollectionFactory() {
		cache = new HashMap<>();
		// Lazy init
		collections = Map.of(
				DictionaryCollection.FAEN, FaEnCollection::new
		);
	}

	public Collection getCollection (DictionaryCollection collectionName)
			throws KeySelectorException
	{
		if (!collections.containsKey(collectionName)) {
			throw new KeySelectorException("Unknown collection");
		}

		cache.putIfAbsent(collectionName, collections.get(collectionName).get());

		return cache.get(collectionName);
	}

	public static Set<DictionaryCollection> getAvailableCollections() {
		return Set.of(DictionaryCollection.values());
	}
}