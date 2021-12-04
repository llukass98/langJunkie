package ru.lukas.langjunkie.dictionarycollections.factory;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.faen.FaEnCollection;

import javax.xml.crypto.KeySelectorException;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Dmitry Lukashevich
 */
public class CollectionFactory {

	private final static HashMap<String, Collection> collections;

	// TODO: ugly as hell, refactor it
	static {
		collections = new HashMap<>();
		collections.put("faen", new FaEnCollection());
	}

	private CollectionFactory() {}

	public static Collection getCollection (String collectionName)
			throws KeySelectorException
	{
		if (!collections.containsKey(collectionName)) {
			throw new KeySelectorException("Unknown collection");
		}

		return collections.get(collectionName);
	}

	public static Set<String> getCollections() { return collections.keySet(); }

}