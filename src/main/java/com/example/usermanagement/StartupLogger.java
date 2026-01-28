package com.example.usermanagement;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    @PostConstruct
    public void logPort() {
        String port = System.getenv("PORT");
        System.out.println(">>> PORT env = " + port);
    }
}
