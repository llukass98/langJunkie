package ru.lukas.langjunkie;

public class App {

    public static void main( String[] args ) {
	Dictionary dict  = new BAmoozDictionary();
	Dictionary dict2 = new FarsidicDictionary();
	Dictionary dict3 = new FarsidictionaryDictionary();
	Dictionary dict4 = new AbadisDictionary();
	Dictionary dict5 = new FarsidicsDictionary();
	Dictionary dict6 = new DictionaryFarsiDictionary();				

	System.out.println(dict.search(args[0]));
	System.out.println(dict2.search(args[0]));
	System.out.println(dict3.search(args[0]));
	System.out.println(dict4.search(args[0]));
	System.out.println(dict5.search(args[0]));
	System.out.println(dict6.search(args[0]));				
    }
  
}
