# Cricket Tournament Match Scoreboard & Points Table Engine

A Java 17 + Maven console application for recording cricket match results and generating a tournament points table with Net Run Rate (NRR).

## Features
- Register tournament teams.
- Record completed match innings (runs, wickets, overs and balls).
- Automatic win/tie/no-result points.
- NRR calculation using actual balls faced/bowled.
- ICC-style all-out handling: an all-out innings is treated as having used the full innings quota for NRR.
- Points-table sorting: points (descending), then NRR (descending), then team name.
- Input validation and duplicate-match protection.
- JUnit 5 unit tests.

## Run
Requires JDK 17+ and Maven 3.9+.

```bash
mvn test
mvn package
java -jar target/cricket-tournament-engine-1.0.0.jar
```

If your Maven setup does not create an executable jar automatically, run:
```bash
mvn exec:java -Dexec.mainClass="com.cricket.Main"
```
(or run `com.cricket.Main` from your IDE).

## Overs format
Enter overs as whole overs plus balls, e.g. `19.4` means 19 overs and 4 balls, not a decimal fraction. The configured innings quota is used to validate innings and calculate NRR. For an all-out innings, NRR uses the full quota.

## Example
Create teams `Falcons` and `Tigers`, then record:
- Falcons: 165/7 in 20.0 overs
- Tigers: 150/10 in 18.2 overs

Falcons receive 2 points. Their NRR contribution is based on 165 runs in 20 overs; Tigers' all-out innings is treated as 20 overs for NRR.

## Scope / assumptions
This starter engine records completed innings totals, not individual ball-by-ball events. Extras are included in the innings total. Super overs, rain-revised DLS targets, bonus points, and player-level scorecards are not implemented.
