package com.example.usermanagement.databasewarmuprunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.usermanagement.repository.UserRepository;

@Component
public class DatabaseWarmupRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    public DatabaseWarmupRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            userRepository.count(); // wakes DB immediately
            System.out.println("Database warmup completed");
        } catch (Exception e) {
            System.out.println("Database warmup failed: " + e.getMessage());
        }
    }
}

