package ru.lukas.langjunkie.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dmitry Lukashevich
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class CardDto {

    private Long id;
    private String frontSide;
    private String backSide;
    private String language;
    private String word;
    private MultipartFile picture;
    private String picturePath;
}
