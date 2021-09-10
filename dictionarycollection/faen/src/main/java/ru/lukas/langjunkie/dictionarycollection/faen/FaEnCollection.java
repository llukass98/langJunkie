package ru.lukas.langjunkie.dictionarycollection.faen;

import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;

import java.util.ArrayList;

public class FaEnCollection extends Collection {
	public FaEnCollection() {
		collectionName = "faen";
		collection = new ArrayList<>();
		collection.add(new BAmoozDictionary());
		collection.add(new FarsidicDictionary());
		collection.add(new DictionaryFarsiDictionary());
		collection.add(new FarsidictionaryDictionary());
		collection.add(new FarsidicsDictionary());
		collection.add(new AbadisDictionary());
	}
}
