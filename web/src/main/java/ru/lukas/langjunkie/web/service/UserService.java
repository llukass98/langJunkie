package ru.lukas.langjunkie.web.service;

import org.springframework.stereotype.Service;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.UserRepository;

/**
 * @author Dmitry Lukashevich
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
