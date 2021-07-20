package ru.lukas.langjunkie;

public class App {

    public static void main( String[] args ) {
	Dictionary dict = BAmoozDictionary.getInstance();
	Dictionary dict2 = FarsidicDictionary.getInstance();
	Dictionary dict3 = FarsidictionaryDictionary.getInstance();
	Dictionary dict4 = AbadisDictionary.getInstance();
	Dictionary dict5 = FarsidicsDictionary.getInstance();
	Dictionary dict6 = DictionaryFarsiDictionary.getInstance();				

	System.out.println(dict.search(args[0]));
	System.out.println(dict2.search(args[0]));
	System.out.println(dict3.search(args[0]));
	System.out.println(dict4.search(args[0]));
	System.out.println(dict5.search(args[0]));
	System.out.println(dict6.search(args[0]));				
    }
  
}
