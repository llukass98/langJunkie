package ru.lukas.langjunkie.dictionarycollection.faen;

import java.util.ArrayList;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;
    
public class FaEnCollection extends Collection {
    public FaEnCollection() {
	collectionName = "faen";
	collection = new ArrayList<Dictionary>();
	collection.add(new BAmoozDictionary());
	collection.add(new FarsidicDictionary());
	collection.add(new DictionaryFarsiDictionary());
	collection.add(new FarsidictionaryDictionary());	
	collection.add(new FarsidicsDictionary());
	collection.add(new AbadisDictionary());
    }
}
