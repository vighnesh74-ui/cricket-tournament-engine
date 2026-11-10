package com.cricket.model;

public record Standing(String teamName, int played, int won, int lost, int tied, int noResult,
                       int points, long runsFor, long ballsFaced, long runsAgainst, long ballsBowled,
                       double netRunRate) {}
