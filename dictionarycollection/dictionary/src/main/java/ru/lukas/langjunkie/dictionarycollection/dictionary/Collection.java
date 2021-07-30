package ru.lukas.langjunkie.dictionarycollection.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
    
public abstract class Collection {
    protected ArrayList<Dictionary> collection;
    protected String collectionName;
    
    public ArrayList<Dictionary> getDictionaries() {
	return collection;
    }

    public String getCollectionName() {
	return collectionName;
    }        

    public ArrayList<HashMap> search(String word) {
	ArrayList<HashMap> results = new ArrayList<HashMap>();
	ArrayList<Future<HashMap>> tasks = new ArrayList<Future<HashMap>>();

	// run all threads and save 'em in an array
	for (Dictionary dictionary : collection) {
	    Callable<HashMap> thread = () -> { return dictionary.search(word); };
	    ExecutorService service = Executors.newSingleThreadExecutor();
	    tasks.add(service.submit(thread));
	}
	// query each task if it's done and get the results
	for (Future<HashMap> task : tasks) {
	    HashMap result = new HashMap();

	    try {
		result = task.get();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    if (!((ArrayList) result.get("results")).isEmpty()) {
		results.add(result);
	    }	    
	}

	return results;
    }
}
