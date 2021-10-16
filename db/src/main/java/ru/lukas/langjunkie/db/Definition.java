package ru.lukas.langjunkie.db;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Definition(String language,
                      String word,
                      Map<String, Serializable> dictionary)
    {
        this.word = word;
        this.language = language;
        definitions = (ArrayList<String>) dictionary.get("results");
        synonyms = (ArrayList<String>) dictionary.get("synonyms");
        examples = (ArrayList<String>) dictionary.get("examples");
        name = (String) dictionary.get("name");
        link = (String) dictionary.get("link");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" +
                "updatedOn=" + updatedOn + ", " +
                "createdOn=" + createdOn +"]";
    }
}