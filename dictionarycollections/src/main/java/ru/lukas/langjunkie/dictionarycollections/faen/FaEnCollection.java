package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.nodes.Document;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.JsoupRequest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;

import java.util.List;

/**
 * Persian to English collection
 *
 * @author Dmitry Lukashevich
 */
public class FaEnCollection extends Collection {

	private static final Request<Document> jsoupRequest = new JsoupRequest();

	public FaEnCollection() {
		super("faen", List.of(
				new BAmoozDictionary(jsoupRequest),
				new FarsidicDictionary(jsoupRequest),
				new DictionaryFarsiDictionary(jsoupRequest),
				new FarsidictionaryDictionary(jsoupRequest),
				new FarsidicsDictionary(jsoupRequest),
				new AbadisDictionary(jsoupRequest)
		));
	}
}