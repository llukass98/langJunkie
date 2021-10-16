package ru.lukas.langjunkie.dictionarycollection.factory;

import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollection.faen.FaEnCollection;

import javax.xml.crypto.KeySelectorException;
import java.util.HashMap;
import java.util.Set;

public class CollectionFactory {

	private final static HashMap<String, Collection> collections;

	static {
		collections = new HashMap<>();
		collections.put("faen", new FaEnCollection());
	}

	private CollectionFactory() {}

	public static Set<String> getCollections() {
		return collections.keySet();
	}

	public static Collection getCollection (String collectionName)
			throws KeySelectorException
	{
		if (!collections.containsKey(collectionName)) {
			throw new KeySelectorException("Unknown collection");
		}

		return collections.get(collectionName);
	}
}