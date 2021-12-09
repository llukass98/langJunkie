package ru.lukas.langjunkie.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author Dmitry Lukashevich
 */
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Word {

    private String word;
}
