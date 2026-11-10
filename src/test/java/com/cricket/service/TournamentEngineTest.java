package com.cricket.service;

import com.cricket.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TournamentEngineTest {
    @Test void winnerGetsTwoPointsAndTableSortsByPoints() {
        TournamentEngine e = new TournamentEngine();
        e.addTeam("Falcons"); e.addTeam("Tigers");
        e.recordMatch("Falcons", new Innings(165, 7, 20, 0, 20),
            "Tigers", new Innings(150, 10, 18, 2, 20));
        var table = e.getPointsTable();
        assertEquals("Falcons", table.get(0).teamName());
        assertEquals(2, table.get(0).points());
        assertEquals(0, table.get(1).points());
    }

    @Test void tieGivesOnePointToEachTeam() {
        TournamentEngine e = new TournamentEngine();
        e.addTeam("A"); e.addTeam("B");
        e.recordMatch("A", new Innings(100, 8, 20, 0, 20),
            "B", new Innings(100, 9, 20, 0, 20));
        assertEquals(1, e.getPointsTable().get(0).points());
        assertEquals(1, e.getPointsTable().get(1).points());
    }

    @Test void rejectsOversBeyondQuota() {
        assertThrows(IllegalArgumentException.class, () -> new Innings(50, 2, 20, 1, 20));
    }
}
