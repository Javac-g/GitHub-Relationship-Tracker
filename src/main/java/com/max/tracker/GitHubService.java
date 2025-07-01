package com.max.tracker;

import org.kohsuke.github.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String token;

    private static final String STATE_FILE = "previous_followers.json";

    public void refresh() {
        if (token == null || token.isEmpty()) {
            System.out.println("Set github.token in application.properties");
            return;
        }

        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            GHMyself me = github.getMyself();

            // follow me
            Set<String> followers = me.getFollowers().stream()
                    .map(GHUser::getLogin)
                    .collect(Collectors.toSet());

            // I follow
            Set<String> following = me.getFollows().stream()
                    .map(GHUser::getLogin)
                    .collect(Collectors.toSet());

            // Previously (to detect unsubscribed)
            Set<String> prev = loadPreviousFollowers();

            // People I used to be followed by
            Set<String> unsubscribed = new HashSet<>(prev);
            unsubscribed.removeAll(followers);

            // People who don't follow me back
            Set<String> notSubscribed = new HashSet<>(following);
            notSubscribed.removeAll(followers);

            // Mutual follows 
            Set<String> mutual = new HashSet<>(followers);
            mutual.retainAll(following);

            Set<String> notFollowing = new HashSet<>(followers);
            notFollowing.removeAll(following);
            // Export all
            ExcelExporter.export(
                    followers,
                    following,
                    mutual,
                    notSubscribed,
                    unsubscribed,
                    notFollowing
            );

            saveFollowers(followers);

            System.out.println("Report generated: github_followers_report.xlsx");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
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
