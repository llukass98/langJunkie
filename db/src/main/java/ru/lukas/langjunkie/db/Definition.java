package ru.lukas.langjunkie.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Entity
public class Definition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;
    private String word;
    private String name;
    private String link;
    private ArrayList<String> definitions;
    private ArrayList<String> synonyms;
    private ArrayList<String> examples;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name="updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public Definition() {}

    public Definition(String language,
                      String word,
                      HashMap<String, Serializable> dictionary)
    {
        this.word = word;
        this.language = language;
        definitions = (ArrayList<String>) dictionary.get("results");
        synonyms = (ArrayList<String>) dictionary.get("synonyms");
        examples = (ArrayList<String>) dictionary.get("examples");
        name = (String) dictionary.get("name");
        link = (String) dictionary.get("link");
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getWord() {
        return word;
    }

    public String getDictionary() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "updatedOn = " + updatedOn + ")";
    }
}