package ru.lukas.langjunkie.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CollectionsDto {

    private final Set<String> collections;
}