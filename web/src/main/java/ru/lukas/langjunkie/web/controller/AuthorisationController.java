package ru.lukas.langjunkie.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class AuthorisationController {

    private final UserCardWordMapper userCardWordMapper;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthorisationController(UserCardWordMapper userCardWordMapper,
                                   UserService userService,
                                   BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userCardWordMapper = userCardWordMapper;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/signin")
    public String signIn(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            modelMap.put("signin_failure", false);

            return "signin";
        }
        // if already signed-in redirect to /
        return "redirect:/";
    }

    @GetMapping("/signin_failure")
    public String signInFailure(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            modelMap.put("signin_failure", true);

            return "signin";
        }
        // if already signed-in redirect to /
        return "redirect:/";
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
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole("ROLE_USER");

        userService.saveUser(user);

        return "redirect:/signup";
    }

    @GetMapping("/logout")
    public String logout() { return "index"; }
}
