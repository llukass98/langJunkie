package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lukas.langjunkie.web.component.UserCardWordMapper;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.service.UserService;

import java.util.Collections;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class RegistrationController {

    private final UserCardWordMapper userCardWordMapper;
    private final UserService userService;

    public RegistrationController(UserCardWordMapper userCardWordMapper, UserService userService) {
        this.userCardWordMapper = userCardWordMapper;
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUp() { return "signup"; }

    @PostMapping("/signup")
    public String createUser(@RequestParam (name = "full_name") String fullName,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam (name ="pass") String password)
    {
        UserDto userDto = new UserDto();
        userDto.setFullName(fullName);
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setCards(Collections.emptyList());
        userDto.setIsActive(true);

        User user = userCardWordMapper.toUserModel(userDto);
        user.setPassword(password);
        user.setRole("ROLE_USER");

        userService.saveUser(user);

        return "redirect:/signup";
    }
}
