package ru.lukas.langjunkie.loader;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollection.factory.CollectionFactory;
import java.util.Map;

public class ClipboardWatcher extends Thread {
    private Collection collection;
    private Clipboard clipboard;
    private String collectionName;
    private Boolean isRunning = false;

    public ClipboardWatcher(String collectionName) {
	this.collectionName = collectionName;
	try {
	    collection = CollectionFactory.getCollection(collectionName);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public String getCollectionName() {
	return collectionName;
    }

    public void stopWatching() {
	isRunning = false;
    }

    @Override
    public void run() {
	String oldData = "";
	isRunning = true;

	while (isRunning){
	    try {
		// skip if data is not a string
		if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
		    continue;
		}
		// if data is a string proceed as usual
		String data = (String) clipboard.getData(DataFlavor.stringFlavor);
		// skip if data hasn't changed		
		if (data.equals(oldData)) {
		    continue;
		}
		oldData = data;
		for (Map result : collection.search(data)) {
		    System.out.print(result.get("name")+"------");
		    System.out.println("Searched word: "+data);
		    System.out.println(result.get("results"));
		    System.out.print("-----------------------------------");
		    System.out.println("-----------------------------------");
		    System.out.print("===================================");
		    System.out.println("===================================");
		}
		System.out.print("***********************************");
		System.out.println("***********************************");
		System.out.print("***********************************");
		System.out.println("***********************************");
		Thread.sleep(200);
	    } catch (UnsupportedFlavorException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
