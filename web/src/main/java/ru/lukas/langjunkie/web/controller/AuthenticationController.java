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

    private static final String REDIRECT_TO_INDEX = "redirect:/";
    private static final String SIGNUP_VIEW = "signup";
    private static final String SIGNIN_VIEW = "signin";

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signIn() { return isSignedIn() ? REDIRECT_TO_INDEX : SIGNIN_VIEW; }

    @GetMapping("/signin-failure")
    public String signInFailure(ModelMap modelMap) {
        if (isSignedIn()) { return REDIRECT_TO_INDEX; }

        modelMap.put("signin_failure", true);

        return SIGNIN_VIEW;
    }

    @GetMapping("/signup")
    public String signUp() { return SIGNUP_VIEW; }

    @PostMapping("/signup")
    public String createUser(@Valid CreateUserDto createUserDto,
                             BindingResult bindingResult,
                             ModelMap modelMap)
    {
        if (bindingResult.hasErrors()) {
            modelMap.put("errors", bindingResult.getAllErrors());

            return SIGNUP_VIEW;
        }

        userService.saveUser(createUserDto);

        return REDIRECT_TO_INDEX;
    }

    // Exception handlers ===========================================================
    // Unique username or email constraint violation
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleUserConstraintViolation(ConstraintViolationException e) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("error", e.getConstraintName());
        modelAndView.setViewName(SIGNUP_VIEW);

        return modelAndView;
    }

    // Helpers =====================================================================
    private boolean isSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }
}
