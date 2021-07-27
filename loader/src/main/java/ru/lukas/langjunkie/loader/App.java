package ru.lukas.langjunkie.loader;

import ru.lukas.langjunkie.dictionarycollection.faen.*;

public class App {
    public static void main( String[] args ) {
	String word = args.length > 0 ? String.join(" ", args) : "";
	BAmoozDictionary dict  = new BAmoozDictionary();
	FarsidicDictionary dict2 = new FarsidicDictionary();
	DictionaryFarsiDictionary dict3 = new DictionaryFarsiDictionary();
	FarsidictionaryDictionary dict4 = new FarsidictionaryDictionary();
	FarsidicsDictionary dict5 = new FarsidicsDictionary();
	AbadisDictionary dict6 = new AbadisDictionary();

	System.out.println(dict.search(word));
	System.out.println(dict2.search(word));
	System.out.println(dict3.search(word));
	System.out.println(dict4.search(word));
	System.out.println(dict5.search(word));
	System.out.println(dict6.search(word));				

    }
}
