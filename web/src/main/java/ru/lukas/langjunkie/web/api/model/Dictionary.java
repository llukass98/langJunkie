package ru.lukas.langjunkie.web.api.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DictionaryCollection language;

    private String word;
    private String name;
    private String link;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> definitions;
}