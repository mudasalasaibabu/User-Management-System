package com.example.usermanagement;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupTimeLogger {

    private long startTime;

    @PostConstruct
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartupTime() {
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("🚀 Application started in " + timeTaken + " ms");
    }
}
