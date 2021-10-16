package ru.lukas.langjunkie.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class Definitions {

    private final int status;
    private final String collection;
    private final String searched_word;
    private final List<Map<String, Serializable>> definitions;
}