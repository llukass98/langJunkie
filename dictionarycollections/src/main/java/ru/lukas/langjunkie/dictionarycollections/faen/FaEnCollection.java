package ru.lukas.langjunkie.dictionarycollections.faen;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;

import java.util.List;

// Persian to English collection
public class FaEnCollection extends Collection {
	public FaEnCollection() {
		collectionName = "faen";
		collection = List.of(
				new BAmoozDictionary(),
				new FarsidicDictionary(),
				new DictionaryFarsiDictionary(),
				new FarsidictionaryDictionary(),
				new FarsidicsDictionary(),
				new AbadisDictionary()
		);
	}
}