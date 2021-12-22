package ru.lukas.langjunkie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "username")
@Builder
public class UserDto {

    private String username;
    private String email;
    private String fullname;
    private List<CardDto> cards;
    private Boolean isActive;
}
