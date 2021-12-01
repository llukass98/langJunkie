package ru.lukas.langjunkie.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class CollectionsDto {

    private final Set<String> collections;
}