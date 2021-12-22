package ru.lukas.langjunkie.web.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.component.UserMapper;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.RoleRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import java.util.Collections;

/**
 * @author Dmitry Lukashevich
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
    }

    @Override
    public UserDto getUserByUsernameAsDto(String username) {
        return userMapper.toUserDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username")));
    }

    @Override
    public void saveUser(CreateUserDto createUserDto) {
        createUserDto.setCards(Collections.emptyList());
        createUserDto.setIsActive(true);

        User user = userMapper.toUserModel(createUserDto);
        user.setRole(roleRepository.findByName(RoleRepository.ROLE_USER).orElseThrow());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
