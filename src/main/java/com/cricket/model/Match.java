package com.cricket.model;

import java.util.UUID;

public record Match(String id, Team teamA, Team teamB, Innings inningsA, Innings inningsB, int pointsPerWin) {
    public Match(Team teamA, Team teamB, Innings inningsA, Innings inningsB) {
        this(UUID.randomUUID().toString(), teamA, teamB, inningsA, inningsB, 2);
    }
    public Match {
        if (teamA == null || teamB == null || inningsA == null || inningsB == null) throw new IllegalArgumentException("Match details cannot be null.");
        if (teamA.equals(teamB)) throw new IllegalArgumentException("A team cannot play against itself.");
        if (inningsA.quotaOvers() != inningsB.quotaOvers()) throw new IllegalArgumentException("Both innings must use the same overs quota.");
        if (pointsPerWin <= 0) throw new IllegalArgumentException("Points per win must be positive.");
    }
    public boolean isTie() { return inningsA.runs() == inningsB.runs(); }
    public Team winner() { return isTie() ? null : (inningsA.runs() > inningsB.runs() ? teamA : teamB); }
}
