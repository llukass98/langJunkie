package ru.lukas.langjunkie.api;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Definitions {

    private final int status;
    private final String collection;
    private final String searched_word;
    private final ArrayList<HashMap<String, Serializable>> definitions;
}