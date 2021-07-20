package ru.lukas.langjunkie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;  
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.ArrayList;
import org.jsoup.nodes.Document;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryFarsiDictionaryTest
{
    HashMap expected;
        
    @Before
    public void setUp() {
	expected = new HashMap();

	expected.put("language", "faen");
	expected.put("name", "dictionary-farsi");
	expected.put("link", "http://www.dictionary-farsi.com");
	expected.put("searched_word", "");
	expected.put("results", new ArrayList<String>());
	expected.put("examples", new ArrayList<String>());
	expected.put("synonyms", new ArrayList<String>());
    }

    @Test
    public void shouldAnswerWithTrue() throws Exception {
	DictionaryFarsiDictionary dic = DictionaryFarsiDictionary.getInstance();
	HashMap result = new HashMap();
	String 	word = "dfdfdfd";
	Dictionary conn = mock(Dictionary.class);
	
	when(conn.makeRequest("https://test.org", "payload")).thenReturn(new Document("https://test.org"));
	expected.put("searched_word", word);
	result = dic.search(word);

	assertThat(result, is(expected));
    }
    
}
