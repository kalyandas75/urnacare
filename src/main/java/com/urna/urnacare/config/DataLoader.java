package com.urna.urnacare.config;

import com.urna.urnacare.domain.User;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!this.userRepository.findOneByEmailIgnoreCase("admin@urnacare.com").isPresent()) {
            User system = new User();
            system.setPassword(passwordEncoder.encode("changeme123"));
            system.setActivated(true);
            system.setAuthority(AuthoritiesConstants.ADMIN);
            system.setEmail("system@urnacare.com");
            system.setFirstName("System");
            system.setLastName("System");
            userRepository.save(system);
            User admin = new User();
            admin.setPassword(passwordEncoder.encode("changeme123"));
            admin.setActivated(true);
            admin.setAuthority(AuthoritiesConstants.ADMIN);
            admin.setEmail("admin@urnacare.com");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            userRepository.save(admin);
            User anonymous = new User();
            anonymous.setPassword(passwordEncoder.encode("changeme123"));
            anonymous.setActivated(true);
            anonymous.setAuthority(AuthoritiesConstants.ANONYMOUS);
            anonymous.setEmail("anonymous@urnacare.com");
            anonymous.setFirstName("Anonymous");
            anonymous.setLastName("User");
            userRepository.save(anonymous);
        }
    }
}
