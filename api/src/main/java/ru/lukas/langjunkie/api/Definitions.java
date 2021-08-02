package ru.lukas.langjunkie.api;

import java.util.ArrayList;
import java.util.HashMap;

public class Definitions {
    private final String collection;
    private final String searched_word;
    private final ArrayList<HashMap> definitions;
    
    public Definitions(String collection,
		       String searched_word,
		       ArrayList<HashMap> definitions)
    {
	this.collection = collection;
	this.searched_word = searched_word;
	this.definitions = definitions;
    }

    public String getCollection() {
	return collection;
    }

    public String getSearched_word() {
	return searched_word;
    }

    public ArrayList<HashMap> getDefinitions() {
	return definitions;
    }
    
}
