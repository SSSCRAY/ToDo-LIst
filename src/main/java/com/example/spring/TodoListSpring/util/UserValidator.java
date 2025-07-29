package com.example.spring.TodoListSpring.util;

import com.example.spring.TodoListSpring.models.User;
import com.example.spring.TodoListSpring.service.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final CustomDetailsService customDetailsService;

    @Autowired
    public UserValidator(CustomDetailsService customDetailsService) {
        this.customDetailsService = customDetailsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            customDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок пользователь не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем существует");
    }
}
