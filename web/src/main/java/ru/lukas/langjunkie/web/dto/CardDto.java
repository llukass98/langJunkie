package ru.lukas.langjunkie.web.dto;

import lombok.*;

/**
 * @author Dmitry Lukashevich
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class CardDto {

    private String frontSide;
    private String backSide;
    private WordDto word;
}
