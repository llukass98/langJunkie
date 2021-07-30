package ru.lukas.langjunkie.loader;


public class App {
    public static void main( String[] args ) {
	String word = args.length > 0 ? String.join(" ", args) : "";
	ClipboardWatcher clipboard = new ClipboardWatcher(args[0]);
	
	try {
	    clipboard.start();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
