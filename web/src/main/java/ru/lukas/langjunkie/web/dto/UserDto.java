package ru.lukas.langjunkie.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;
    private String email;
    private String fullname;
    private List<CardDto> cards;
    private Boolean isActive;
}
