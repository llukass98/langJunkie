package ru.lukas.langjunkie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.Before;
import java.util.HashMap;
import java.util.ArrayList;

public class FarsidicsDictionaryTest
{
    HashMap expected;
        
    @Before
    public void setUp() {
	expected = new HashMap();

	expected.put("language", "faen");
	expected.put("name", "farsidics");
	expected.put("link", "http://www.farsidics.com");
	expected.put("searched_word", "");
	expected.put("results", new ArrayList<String>());
	expected.put("examples", new ArrayList<String>());
	expected.put("synonyms", new ArrayList<String>());
    }

    @Test
    public void shouldAnswerWithTrue() {
	FarsidicsDictionary dic = FarsidicsDictionary.getInstance();
	HashMap result = new HashMap();
	String 	word = "dfdfdfd";

	expected.put("searched_word", word);
	result = dic.search(word);

	assertThat(result, is(expected));
    }
    
}
