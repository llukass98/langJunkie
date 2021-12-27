package ru.lukas.langjunkie.web.controller;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.service.UserService;

import javax.validation.Valid;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signIn() { return isSignedIn() ? "redirect:/" : "signin"; }

    @GetMapping("/signin-failure")
    public String signInFailure(ModelMap modelMap) {
        if (isSignedIn()) { return "redirect:/"; }

        modelMap.put("signin-failure", true);

        return "signin";
    }

    @GetMapping("/signup")
    public String signUp() { return "signup"; }

    @PostMapping("/signup")
    public String createUser(@Valid CreateUserDto createUserDto,
                             BindingResult bindingResult,
                             ModelMap modelMap)
    {
        if (bindingResult.hasErrors()) {
            modelMap.put("errors", bindingResult.getAllErrors());

            return "signup";
        }

        userService.saveUser(createUserDto);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() { return "index"; }

    // Exception handlers ===========================================================
    // Unique username or email constraint violation
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleUserConstraintViolation(ConstraintViolationException e) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("error", e.getConstraintName());
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    // Helpers =====================================================================
    private Boolean isSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }
}
