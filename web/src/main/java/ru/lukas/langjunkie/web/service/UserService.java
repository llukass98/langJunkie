package ru.lukas.langjunkie.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.dto.UserDto;

/**
 * @author Dmitry Lukashevich
 */
public interface UserService extends UserDetailsService {

    UserDto getUserByUsernameAsDto(String username);

    void saveUser(CreateUserDto createUserDto);
}
