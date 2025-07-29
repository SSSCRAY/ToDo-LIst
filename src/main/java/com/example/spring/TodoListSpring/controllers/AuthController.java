package com.example.spring.TodoListSpring.controllers;

import com.example.spring.TodoListSpring.models.Role;
import com.example.spring.TodoListSpring.models.User;
import com.example.spring.TodoListSpring.service.RegistrationService;
import com.example.spring.TodoListSpring.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




// Создаем кастомную страницу авторизацию
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
    BindingResult bindingResult ) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/registration";


        registrationService.register(user);

        return "redirect:/auth/login";

    }


}
