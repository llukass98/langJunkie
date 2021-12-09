package ru.lukas.langjunkie.web.service;

import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.User;

/**
 * @author Dmitry Lukashevich
 */
public interface UserService {

    UserDto getUserByUsername(String username);

    User getUserByUsernameAsModel(String username);

    void saveUser(UserDto userDto, String password);
}
