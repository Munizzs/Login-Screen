package com.estudos.login_screen.configs;

import com.estudos.login_screen.models.Role;
import com.estudos.login_screen.models.User;
import com.estudos.login_screen.repositories.RoleRepository;
import com.estudos.login_screen.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner dataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            Role adminRole = new Role();
            adminRole.setNome("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setNome("ROLE_USER");
            roleRepository.save(userRole);

            User admin = new User();
            admin.setName("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setEnable(true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        };
    }
}
