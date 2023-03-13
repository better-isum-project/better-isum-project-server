package com.better.isum.project.server;

import java.util.logging.Level;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BetterIsumProjectServer {
    public static void main(String[] args) {
        final MetLogger LOGGER = MetLogger.getInstance();
        LOGGER.LogMessage("info test", Level.INFO);
        LOGGER.LogMessage("config test", Level.CONFIG);
        LOGGER.LogMessage("warning test", Level.WARNING);
        LOGGER.LogMessage("severe test", Level.SEVERE);
        SpringApplication.run(BetterIsumProjectServer.class, args);
    }
}
