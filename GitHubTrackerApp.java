package com.max.tracker;

public class GitHubTrackerApp {
    public static void main(String[] args) {
        GitHubService service = new GitHubService();
        service.refresh();
    }
}
