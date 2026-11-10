package com.cricket.service;

import com.cricket.model.*;
import java.util.*;

public class TournamentEngine {
    private final Map<String, Team> teams = new LinkedHashMap<>();
    private final List<Match> matches = new ArrayList<>();

    public Team addTeam(String name) {
        Team team = new Team(name);
        String key = key(team.getName());
        if (teams.containsKey(key)) throw new IllegalArgumentException("Team already exists: " + name);
        teams.put(key, team);
        return team;
    }

    public Collection<Team> getTeams() { return Collections.unmodifiableCollection(teams.values()); }
    public List<Match> getMatches() { return Collections.unmodifiableList(matches); }

    public Match recordMatch(String teamAName, Innings inningsA, String teamBName, Innings inningsB) {
        Team a = findTeam(teamAName), b = findTeam(teamBName);
        Match match = new Match(a, b, inningsA, inningsB);
        // Repeat fixtures are allowed (for example, home-and-away league formats).
        matches.add(match);
        return match;
    }

    public List<Standing> getPointsTable() {
        Map<Team, Stats> stats = new LinkedHashMap<>();
        teams.values().forEach(t -> stats.put(t, new Stats()));
        for (Match m : matches) {
            Stats a = stats.get(m.teamA()), b = stats.get(m.teamB());
            a.played++; b.played++;
            a.runsFor += m.inningsA().runs(); a.ballsFaced += m.inningsA().ballsForNrr();
            a.runsAgainst += m.inningsB().runs(); a.ballsBowled += m.inningsB().ballsForNrr();
            b.runsFor += m.inningsB().runs(); b.ballsFaced += m.inningsB().ballsForNrr();
            b.runsAgainst += m.inningsA().runs(); b.ballsBowled += m.inningsA().ballsForNrr();
            if (m.isTie()) {
                a.tied++; b.tied++; a.points++; b.points++;
            } else if (m.winner().equals(m.teamA())) {
                a.won++; b.lost++; a.points += m.pointsPerWin();
            } else {
                b.won++; a.lost++; b.points += m.pointsPerWin();
            }
        }
        List<Standing> result = new ArrayList<>();
        stats.forEach((team, s) -> {
            double nrr = (s.ballsFaced == 0 || s.ballsBowled == 0) ? 0.0
                : (s.runsFor / (s.ballsFaced / 6.0)) - (s.runsAgainst / (s.ballsBowled / 6.0));
            result.add(new Standing(team.getName(), s.played, s.won, s.lost, s.tied, s.noResult,
                s.points, s.runsFor, s.ballsFaced, s.runsAgainst, s.ballsBowled, nrr));
        });
        result.sort(Comparator.comparingInt(Standing::points).reversed()
            .thenComparing(Comparator.comparingDouble(Standing::netRunRate).reversed())
            .thenComparing(Standing::teamName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private Team findTeam(String name) {
        Team t = teams.get(key(name));
        if (t == null) throw new IllegalArgumentException("Unknown team: " + name + ". Add it first.");
        return t;
    }
    private static String key(String s) { return s.trim().toLowerCase(Locale.ROOT); }
    private static class Stats {
        int played, won, lost, tied, noResult, points;
        long runsFor, ballsFaced, runsAgainst, ballsBowled;
    }
}
