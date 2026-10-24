package com.example.Demo.config;

import com.example.Demo.entity.Role;
import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    /** Creates the first admin at startup (credentials come from env vars) if it doesn't exist. */
    @Bean
    CommandLineRunner seedAdmin(UserRepository users,
                                PasswordEncoder encoder,
                                @Value("${app.admin.username}") String username,
                                @Value("${app.admin.password}") String password) {
        return args -> {
            if (users.findByUsername(username).isEmpty()) {
                users.save(new User(username, encoder.encode(password), Role.ROLE_ADMIN));
            }
        };
    }
}
