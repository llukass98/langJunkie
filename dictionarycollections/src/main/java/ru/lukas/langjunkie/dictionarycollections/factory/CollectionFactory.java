package ru.lukas.langjunkie.dictionarycollections.factory;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.faen.FaEnCollection;

import javax.xml.crypto.KeySelectorException;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Dmitry Lukashevich
 */
public class CollectionFactory {

	private final static HashMap<DictionaryCollection, Collection> collections;

	// TODO: ugly as hell, refactor it
	static {
		collections = new HashMap<>();
		collections.put(DictionaryCollection.FAEN, new FaEnCollection());
	}

	private CollectionFactory() {}

	public static Collection getCollection (DictionaryCollection collectionName)
			throws KeySelectorException
	{
		if (!collections.containsKey(collectionName)) {
			throw new KeySelectorException("Unknown collection");
		}

		return collections.get(collectionName);
	}

	public static Set<DictionaryCollection> getAllCollections() {
		return Set.of(DictionaryCollection.values());
	}
}