package ru.lukas.langjunkie.dictionarycollection.factory;

import java.util.HashMap;
import ru.lukas.langjunkie.dictionarycollection.faen.FaEnCollection;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;
import javax.xml.crypto.KeySelectorException;

public class CollectionFactory {
	private static HashMap<String, Collection> collections;

	private CollectionFactory() {}

	public static Collection getCollection (String collectionName)
			throws KeySelectorException
	{
		if (collections == null) {
			collections = new HashMap<>();
			collections.put("faen", new FaEnCollection());
		}

		if (!collections.containsKey(collectionName)) {
			throw new KeySelectorException("Unknown collection");
		}

		return collections.get(collectionName);
	}
}
