package ru.lukas.langjunkie.web.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.lukas.langjunkie.web.api.exception.InvalidPasswordException;
import ru.lukas.langjunkie.web.component.UserMapper;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.UserRepository;

import java.util.Collections;

/**
 * @author Dmitry Lukashevich
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.toUserDto(userRepository.findByUsername(username));
    }

    @Override
    public User getUserByUsernameAsModel(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(UserDto userDto, String password) {
        userDto.setCards(Collections.emptyList());
        userDto.setIsActive(true);

        User user = userMapper.toUserModel(userDto);
        user.setPassword(password);
        user.setRole("ROLE_USER");

        if (user.getPassword().length() < 6 || user.getPassword().length() > 15) {
            throw new InvalidPasswordException("Password length must be between 6 and 15 characters");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
