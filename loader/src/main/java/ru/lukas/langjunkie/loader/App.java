package ru.lukas.langjunkie.loader;

public class App {
	public static void main( String[] args ) {
		ClipboardWatcher clipboard = new ClipboardWatcher(args[0]);
	
		try {
			clipboard.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
