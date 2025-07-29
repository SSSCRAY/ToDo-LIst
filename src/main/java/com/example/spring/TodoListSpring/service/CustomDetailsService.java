package com.example.spring.TodoListSpring.service;

import com.example.spring.TodoListSpring.models.User;
import com.example.spring.TodoListSpring.repositories.UserRepository;
import com.example.spring.TodoListSpring.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new CustomUserDetails(user.get());


    }
}
