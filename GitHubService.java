package com.max.tracker;

import org.kohsuke.github.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GitHubService {
    private static final String TOKEN = System.getenv("GITHUB_TOKEN");
    private static final String STATE_FILE = "previous_followers.json";

    public void refresh() {
        if (TOKEN == null) {
            System.out.println("⚠️  Set GITHUB_TOKEN env variable.");
            return;
        }

        try {
            GitHub github = new GitHubBuilder().withOAuthToken(TOKEN).build();
            GHMyself me = github.getMyself();

            Set<String> followers = me.getFollowers().stream()
                    .map(GHUser::getLogin).collect(Collectors.toSet());

            Set<String> following = me.getFollows().stream()
                    .map(GHUser::getLogin).collect(Collectors.toSet());

            Set<String> prev = loadPreviousFollowers();

            Set<String> unsubscribed = new HashSet<>(prev);
            unsubscribed.removeAll(followers);

            Set<String> notSubscribed = new HashSet<>(following);
            notSubscribed.removeAll(followers);

            ExcelExporter.export(followers, notSubscribed, unsubscribed);
            saveFollowers(followers);

            System.out.println("✅ Report generated: github_followers_report.xlsx");

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private Set<String> loadPreviousFollowers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return new HashSet<>(Arrays.asList(
                    mapper.readValue(new File(STATE_FILE), String[].class)
            ));
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    private void saveFollowers(Set<String> followers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(STATE_FILE), followers);
    }
}
