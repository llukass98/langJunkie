package ru.lukas.langjunkie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;  
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Matchers.anyString;

import org.powermock.api.mockito.PowerMockito;  
import org.powermock.core.classloader.annotations.PrepareForTest;  
import org.powermock.modules.junit4.PowerMockRunner;  

import java.util.HashMap;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class) 
public class AbadisDictionaryTest
{
    HashMap expected;

    @InjectMocks
    Connection mockedConn;
        
    @Before
    public void setUp() {
	initMocks(this);
	expected = new HashMap();

	expected.put("language", "faen");
	expected.put("name", "abadis");
	expected.put("link", "https://abadis.ir");
	expected.put("searched_word", "");
	expected.put("results", new ArrayList<String>());
	expected.put("examples", new ArrayList<String>());
	expected.put("synonyms", new ArrayList<String>());
    }


    @Test
    public void shouldReturnCorrectJSON() throws Exception {	
	Dictionary dic = AbadisDictionary.getInstance();	
	HashMap result = new HashMap();
	String 	word = "dfdfdfd";

	PowerMockito.mockStatic(Jsoup.class);
	PowerMockito.when(Jsoup.connect("https;//www.test.org")).thenReturn(mockedConn);
	when(mockedConn.get()).thenReturn(new Document("dfdfdfd"));
	//	doReturn(new Document("https://test.org")).when(dic).makeRequest(nullable(String.class));
	     //	PowerMockito.doReturn(new Document("https://test.org")).when(PowerMockito.spy(dic), "makeRequest", anyString());	
	expected.put("searched_word", word);
	result = dic.search(word);

	assertThat(result, is(expected));
    }
    
}
