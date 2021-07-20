package ru.lukas.langjunkie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.Before;
import java.util.HashMap;
import java.util.ArrayList;

public class FarsidictionaryDictionaryTest
{
    HashMap expected;
        
    @Before
    public void setUp() {
	expected = new HashMap();

	expected.put("language", "faen");
	expected.put("name", "farsidictionary");
	expected.put("link", "https://www.farsidictionary.net");
	expected.put("searched_word", "");
	expected.put("results", new ArrayList<String>());
	expected.put("examples", new ArrayList<String>());
	expected.put("synonyms", new ArrayList<String>());
    }

    @Test
    public void shouldAnswerWithTrue() {
	FarsidictionaryDictionary dic = FarsidictionaryDictionary.getInstance();
	HashMap result = new HashMap();
	String 	word = "dfdfdfd";

	expected.put("searched_word", word);
	result = dic.search(word);

	assertThat(result, is(expected));
    }
    
}
