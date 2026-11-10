package com.cricket;

import com.cricket.model.*;
import com.cricket.service.TournamentEngine;
import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final TournamentEngine ENGINE = new TournamentEngine();

    public static void main(String[] args) {
        System.out.println("\n=== CRICKET TOURNAMENT SCOREBOARD & POINTS TABLE ===");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Add team  2. List teams  3. Record match  4. View points table  5. Exit");
            System.out.print("Choose: ");
            String choice = IN.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addTeam();
                    case "2" -> ENGINE.getTeams().forEach(System.out::println);
                    case "3" -> recordMatch();
                    case "4" -> showTable();
                    case "5" -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Tournament session ended.");
    }

    private static void addTeam() {
        System.out.print("Team name: ");
        System.out.println("Added: " + ENGINE.addTeam(IN.nextLine()));
    }

    private static void recordMatch() {
        System.out.print("Innings overs quota (e.g. 20): ");
        int quota = Integer.parseInt(IN.nextLine().trim());
        System.out.print("Team A: "); String a = IN.nextLine();
        Innings ia = readInnings("Team A", quota);
        System.out.print("Team B: "); String b = IN.nextLine();
        Innings ib = readInnings("Team B", quota);
        Match m = ENGINE.recordMatch(a, ia, b, ib);
        if (m.isTie()) System.out.println("Result: Match tied. Both teams receive 1 point.");
        else System.out.println("Result: " + m.winner().getName() + " won and received 2 points.");
    }

    private static Innings readInnings(String label, int quota) {
        System.out.println(label + " innings:");
        System.out.print("Runs: "); int runs = Integer.parseInt(IN.nextLine().trim());
        System.out.print("Wickets: "); int wickets = Integer.parseInt(IN.nextLine().trim());
        System.out.print("Overs (e.g. 19.4): "); String value = IN.nextLine().trim();
        String[] parts = value.split("\\.", -1);
        if (parts.length > 2 || parts[0].isBlank()) throw new IllegalArgumentException("Use overs notation such as 19.4.");
        int overs = Integer.parseInt(parts[0]);
        int balls = parts.length == 1 ? 0 : Integer.parseInt(parts[1]);
        return new Innings(runs, wickets, overs, balls, quota);
    }

    private static void showTable() {
        System.out.printf("%-18s %3s %3s %3s %3s %6s %8s%n", "TEAM", "P", "W", "L", "T", "PTS", "NRR");
        for (Standing s : ENGINE.getPointsTable())
            System.out.printf("%-18s %3d %3d %3d %3d %6d %8.3f%n",
                s.teamName(), s.played(), s.won(), s.lost(), s.tied(), s.points(), s.netRunRate());
    }
}
