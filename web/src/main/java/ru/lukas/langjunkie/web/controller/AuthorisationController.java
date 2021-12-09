package ru.lukas.langjunkie.web.controller;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.lukas.langjunkie.web.api.exception.InvalidPasswordException;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.service.UserServiceImpl;

import java.util.Set;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class AuthorisationController {

    private final UserServiceImpl userServiceImpl;

    public AuthorisationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/signin")
    public String signIn() { return isSignedIn() ? "redirect:/" : "signin"; }

    @GetMapping("/signin_failure")
    public String signInFailure(ModelMap modelMap) {
        if (isSignedIn()) { return "redirect:/"; }

        modelMap.put("signin_failure", true);

        return "signin";
    }

    @GetMapping("/signup")
    public String signUp() { return "signup"; }

    @PostMapping("/signup")
    public String createUser(UserDto userDto, @RequestParam String password) {
        userServiceImpl.saveUser(userDto, password);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() { return "index"; }

    // Exception handlers ===========================================================
    // TODO: add logging
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleUserConstraintViolation(ConstraintViolationException e)
    {
        ModelAndView modelAndView = new ModelAndView();
        String message = e.getConstraintName().contains("username") ?
                "user_exists" : "email_exists";

        modelAndView.addObject("error", message);
        modelAndView.addObject("signup_failure", true);
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ModelAndView handleUserConstraintViolation(javax.validation.ConstraintViolationException e)
    {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("errors", e.getConstraintViolations());
        modelAndView.addObject("signup_failure", true);
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ModelAndView handleUserConstraintViolation(InvalidPasswordException e) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("errors", Set.of(e));
        modelAndView.addObject("signup_failure", true);
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    // Helpers =====================================================================
    private Boolean isSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }
}
