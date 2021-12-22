package ru.lukas.langjunkie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.lukas.langjunkie.web.validation.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class CreateUserDto {

    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "full name field is mandatory")
    private String fullname;

    @Password
    private String password;

    private List<CardDto> cards;
    private Boolean isActive;
}
