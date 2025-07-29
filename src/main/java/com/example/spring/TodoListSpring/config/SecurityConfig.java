package com.example.spring.TodoListSpring.config;

import com.example.spring.TodoListSpring.service.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomDetailsService customDetailsService;


    @Autowired
    public SecurityConfig(CustomDetailsService customDetailsService) {
        this.customDetailsService = customDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Настройка доступа
                .authorizeHttpRequests(auth -> auth
                                /* Разрешаем доступ без авторизации: */
                                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                                /* Требуем авторизацию для всех остальных запросов: */
                                .anyRequest().hasAnyRole("USER", "ADMIN")

                )
                // Настройка формы входа
                .formLogin(form -> form
                                // передаем страницу для авторизации - передаем форму
                        .loginPage("/auth/login")

                        .loginProcessingUrl("/process_login") // передаем html форму

                        // после успешной аунтификации перенапрявь меня на эту страницу
                        .defaultSuccessUrl("/tasks", true)

                        // после не успешной авторизации обратно возвращает на стр login
                        .failureUrl("/auth/login?error")
                )
                // это удаляется кукис
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")

                );


        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return builder.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
