package ru.lukas.langjunkie.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.dto.UserViewDto;

/**
 * @author Dmitry Lukashevich
 */
public interface UserService extends UserDetailsService {

    UserViewDto getUserViewByUsername(String username);

    void saveUser(CreateUserDto createUserDto);
}
