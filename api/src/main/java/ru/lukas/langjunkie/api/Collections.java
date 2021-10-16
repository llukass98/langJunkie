package ru.lukas.langjunkie.api;

import lombok.Data;

import java.util.Set;

@Data
public class Collections {

    private final int status;
    private final Set<String> collections;
}