package com.cricket.model;

/** A completed innings summary. Overs are represented as legal balls, never as a decimal. */
public record Innings(int runs, int wickets, int overs, int balls, int quotaOvers) {
    public Innings {
        if (runs < 0) throw new IllegalArgumentException("Runs cannot be negative.");
        if (wickets < 0 || wickets > 10) throw new IllegalArgumentException("Wickets must be between 0 and 10.");
        if (overs < 0 || balls < 0 || balls > 5) throw new IllegalArgumentException("Invalid overs notation.");
        if (quotaOvers <= 0) throw new IllegalArgumentException("Overs quota must be positive.");
        if (overs * 6 + balls > quotaOvers * 6) throw new IllegalArgumentException("Overs exceed innings quota.");
        if (wickets == 10 && overs * 6 + balls == 0) throw new IllegalArgumentException("An all-out innings must use at least one ball.");
    }
    public int ballsUsed() { return overs * 6 + balls; }
    public int ballsForNrr() { return wickets == 10 ? quotaOvers * 6 : ballsUsed(); }
    public double oversForNrr() { return ballsForNrr() / 6.0; }
    public String oversDisplay() { return overs + "." + balls; }
}
