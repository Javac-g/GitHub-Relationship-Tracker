package com.max.tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitHubTrackerApp implements CommandLineRunner {
    private final GitHubService service;

    public GitHubTrackerApp(GitHubService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(GitHubTrackerApp.class, args);
    }

    @Override
    public void run(String... args) {
        service.refresh();
    }
}
