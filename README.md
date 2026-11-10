# Cricket Tournament Engine

A lightweight **Java 17 + Maven** console application for managing cricket tournament results and generating points tables with Net Run Rate (NRR).

## Features

- Register teams with duplicate-name protection.
- Record completed match innings and results.
- Award 2 points for a win and 1 point per team for a tie.
- Calculate NRR using actual legal balls.
- Apply full-innings quota for all-out innings.
- Rank teams by points, NRR and team name.
- Validate match and innings data.
- Automated unit tests using JUnit 5.

## Tech Stack

- **Language:** Java 17
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5
- **Architecture:** Layered Architecture

## Project Structure

```text
cricket-tournament-engine/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/cricket/
    │   ├── Main.java
    │   ├── model/
    │   │   ├── Team.java
    │   │   ├── Innings.java
    │   │   ├── Match.java
    │   │   └── Standing.java
    │   └── service/
    │       └── TournamentEngine.java
    └── test/java/com/cricket/service/
        └── TournamentEngineTest.java
```

## Getting Started

**Requirements:** JDK 17+ and Maven 3.9+.

Clone the repository and navigate to the project directory:

```bash
git clone <repository-url>
cd cricket-tournament-engine
```

Run the tests:

```bash
mvn test
```

Build the application:

```bash
mvn package
```

Run the application:

```bash
java -jar target/cricket-tournament-engine-1.0.0.jar
```

Alternatively:

```bash
mvn exec:java -Dexec.mainClass="com.cricket.Main"
```

## How It Works

The application follows a three-layer architecture:

| Layer | Responsibility |
|---|---|
| Model | Teams, innings, matches and standings |
| Service | Tournament management, scoring and NRR |
| Application | Console menu and user interaction |

### Net Run Rate

NRR is calculated as:

```text
NRR = (Runs Scored / Overs Faced)
    - (Runs Conceded / Overs Bowled)
```

Overs are calculated using legal balls rather than decimal fractions. An all-out innings is counted using the full innings quota.

**Overs notation:** `19.4` represents 19 overs and 4 balls, not 19.4 decimal overs.

## Example

For a 20-over match:

| Team | Score |
|---|---:|
| Falcons | 165/7 (20.0 overs) |
| Tigers | 150/10 (18.2 overs) |

**Result:** Falcons win by 15 runs.

| Team | Played | Won | Lost | Points | NRR |
|---|---:|---:|---:|---:|---:|
| Falcons | 1 | 1 | 0 | 2 | +0.750 |
| Tigers | 1 | 0 | 1 | 0 | -0.750 |

## Points System

| Match Result | Points |
|---|---:|
| Win | 2 |
| Tie | 1 each |
| Loss | 0 |

Teams are ranked by:
1. Total points
2. Net Run Rate
3. Team name (alphabetical)

## Scope

This application records completed innings summaries rather than ball-by-ball events. Extras are included in innings totals.

Super overs, DLS adjustments, bonus points, no-result processing, and player-level scorecards are outside the current implementation. Repeat fixtures are permitted.

---

**Built with Java 17 and Maven.**
