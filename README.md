# Boggled - A Java CORBA Multiplayer Word Game

## About the Project
**Boggled** is a multiplayer word game built using Java CORBA. It allows multiple players to connect to a server, authenticate, and compete to form unique words from randomly generated letters. The game requires at least two players to start and runs until one player wins three rounds. An administrator has special privileges to manage players and game settings.

## Features

### Client Module
- **User Authentication**: Players must log in with valid credentials.
- **Game Interface**: Players receive 20 random letters (13 consonants, 7 vowels).
- **Word Submission**: Players can submit words of at least four letters.
- **Word Validation**: The client displays server responses indicating whether a word is valid or not.
- **Scoring System**: Players earn points for unique words.
- **Game Progression**: Displays round results and tracks player wins.
- **Leaderboard Display**: Players can view the **top scores** retrieved from the database.

### Server Module
- **Multiplayer Support via CORBA**: Handles multiple client connections.
- **Authentication Management**: Validates player credentials using a **MySQL database**.
- **Game Session Management**:
    - Ensures at least two players start a game.
    - Assigns random letters per session.
    - Maintains round timers and game flow.
- **Word Verification**: Cross-checks words against `words.txt` to determine validity.
- **Scoring System**: Calculates scores based on unique words.
- **Game Termination**: Ends the game when a player wins 3 rounds.
- **Administrator Privileges**:
    - Manage player accounts (CRUD operations).
    - Modify game settings (waiting time, round duration).
    - Retrieve leaderboard data for client display.
- **Persistent Storage**: Stores player data, scores, and game history in a **MySQL database**.

## Requirements
```markdown
- Java Development Kit (JDK) 8 or later
- Java IDL
- MySQL Database
- CORBA-compliant ORB
- Java GUI framework
```

Developer: Members
- Bravo, Matt Danielle
- Briones, Neil Angelo
- Fabe, Milton Junsel
- Lactaotao, Benny Gil
- Magpili, Dylan Yeoj
- Pascual, Jermaine Bryan
- Viduya, Hans Elijah
