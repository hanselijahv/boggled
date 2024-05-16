package BoggledApp;

import net.team6.boggled.common.model.Player;
import net.team6.boggled.common.model.Settings;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.team6.boggled.common.db.PlayerDAO.playerDAOImpl;
import static net.team6.boggled.common.db.SettingsDAO.settingsDAOImpl;

public class BoggledImplementation extends BoggledPOA {

    private final ConcurrentHashMap<String, WaitingRoom> waitingRooms = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, GameRoom> gameRooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionMap = new HashMap<>();
    private final Set<String> loggedInUsers = new HashSet<>();

    private ORB orb;

    // TODO: FIX THIS
    /*    public int getRoundTime() {
        try {
            List<Settings> roundTimeSetting = settingsDAOImpl.getAll();
            Settings setting = roundTimeSetting.get(0);
            roundTime = setting.getRoundTime();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return roundTime;
    }


    public int getNumRounds() {
        try {
            List<Settings> numOfRoundsSetting = settingsDAOImpl.getAll();
            Settings setting = numOfRoundsSetting.get(0);
            numOfRounds = setting.getRoundsToWin();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return numOfRounds;
    }*/

    public BoggledImplementation() {

    }

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public void login(String username, String password) throws UserNotFoundException, AlreadyLoggedInException {
        Player account = new Player(null, username, password);
        boolean isAuthenticated = false;

        if (loggedInUsers.contains(username)) {
            throw new AlreadyLoggedInException("User already logged in: " + username);
        }

        try {
            isAuthenticated = playerDAOImpl.authenticatePlayer(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!isAuthenticated) {
            throw new UserNotFoundException("User not found!");
        }
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, username);
        loggedInUsers.add(username);
    }

    @Override
    public void joinWaitingRoom(String playerName) {
        WaitingRoom room = null;
        if (!waitingRooms.isEmpty()) {
            room = waitingRooms.values().iterator().next();
        }
        if (room == null) {
            room = new WaitingRoom(playerName);
            waitingRooms.put(playerName, room);
        } else {
            room.joinPlayer(playerName);
            System.out.println(playerName + " joined the waiting room created by " + room.creator);
        }
        System.out.println(getWaitingRoomInfo(playerName));
    }

    @Override
    public String say(Callback objref, String message) {
        return "TO REMOVE/FIX";
    }

    @Override
    public String getWaitingTime(Callback objRef) {
        String remainingTime;
        try {
            WaitingRoom room = waitingRooms.values().iterator().next();
            remainingTime = room.getRemainingTime();
        } catch (Exception e) {
            return "Room has ended!";
        }
        return remainingTime;
    }

    @Override
    public String getWaitingRoomInfo(String playerName) {
        for (WaitingRoom room : waitingRooms.values()) {
            if (room.players.containsKey(playerName)) {
                return "Waiting room created by " + room.creator + " has " + room.players.size() + " players: " + room.players.keySet();
            }
        }
        return "No waiting room found for player: " + playerName;
    }

    @Override
    public String getGameID(Callback objRef, String playerName) {
        try {
            WaitingRoom room = waitingRooms.values().iterator().next();
            return room.getGameId();
        } catch (Exception e){
            return "GAMEID ISN'T AVAILABLE";
        }
    }

    @Override
    public String getLetters(String gameID){
        GameRoom gameRoom = gameRooms.get(gameID);
        List<Character> lettersList = gameRoom.getRandomLetters();
        System.out.println("LETTERS: "+lettersList);
        StringBuilder stringBuilder = new StringBuilder(lettersList.size());
        for(Character character : lettersList) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }


    @Override
    public boolean isGameReadyToStart() {
        WaitingRoom room;
        try {
            room = waitingRooms.values().iterator().next();
        } catch (Exception e) {
            return false;
        }
        return room.isReadyToStart();
    }

    //TODO: FOR GAME ROOM
    @Override
    public boolean submitWord(String word, BooleanHolder isValid, BooleanHolder canForm, StringHolder response) {
        if (word.length() < 4) {
            response.value = "Word must be at least 4 letters long.";
            isValid.value = false;
            return false;
        }

/*
        if (!isValidWord(word)) {
            System.out.println("Word is not found in the dictionary.");
            isValid.value = false;
            return false;
        }*/

        isValid.value = true;
        canForm.value = true;
        response.value = "Word is valid!";
        return true;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }


    private class WaitingRoom {
        private final String creator;
        private final ConcurrentHashMap<String, Boolean> players = new ConcurrentHashMap<>();
        public long startTime;
        private Timer timer;
        private long timerDuration = getWaitingTime();

        private GameRoom gameRoom = new GameRoom();

        private String gameId;

        public WaitingRoom(String creator) {
            gameId = gameRoom.createGameId();
            this.creator = creator;
            startTime = System.currentTimeMillis();
            players.put(creator, true);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isNotEnough()) {
                        System.out.println("Not enough players to start the game.");
                    } else {
                        if (isReadyToStart()) {
                            List<String> playersInWaitingRoom = new ArrayList<>();
                            for (WaitingRoom room : waitingRooms.values()) {
                                playersInWaitingRoom.addAll(room.getPlayers());
                            }
                            gameRooms.put(gameId, gameRoom);
                            System.out.println("GAME STARTED FOR GAME: "+gameId);
                            waitingRooms.clear();
                        }
                    }
                    waitingRooms.remove(creator);
                    System.out.println("Waiting room dissolved: " + creator);
                    timer.cancel();
                }
            }, timerDuration * 1000);
        }

        public void joinPlayer(String playerName) {
            players.put(playerName, true);
        }

        public boolean isReadyToStart() {
            return players.size() >= 2;
        }

        public boolean isNotEnough() {
            return players.size() <= 1;
        }

        public String getRemainingTime() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = Math.max(0, timerDuration - (elapsedTime / 1000));
            long seconds = (remainingTime % 60);
            return String.format("%02d", seconds);
        }

        public List<String> getPlayers() {
            return new ArrayList<>(players.keySet());
        }

        public int getWaitingTime() {
            int duration;
            try {
                List<Settings> waitingTimeSetting = settingsDAOImpl.getAll();
                Settings setting = waitingTimeSetting.get(0);
                duration = setting.getWaitingTime();
                System.out.println(duration);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return duration;
        }

        public String getGameId() {
            return gameId;
        }

    }

    private class GameRoom {

        private static final int VOWEL_COUNT = 7;
        private static final int CONSONANT_COUNT = 13;
        private  List<String> players;
        private  Set<String> winners;
        private final int totalRounds = 3;
        private String gameWinner;
        private int score;
        private int currentRound;
        private Set<String> dictionary;
        private List<Character> randomLetters;
        private List<Character> letters;
        private List<String> words;
        private Map<String, List<String>> submittedWords;

        // validates the word and puts in Map
        public boolean submitWord(String playerName, String word) {
            boolean isValid = canFormWord(word);
            if (isValid) {
                words = submittedWords.getOrDefault(playerName, new ArrayList<>());
            }
            return isValid;
        }

        public GameRoom(List<String> players) {
            this.players = new ArrayList<>(players);
            this.winners = new HashSet<>();
            this.currentRound = 0;
        }
        public GameRoom() {
            randomLetters = generateRandomLetters();
        }

        private void loadDictionary() {
            dictionary = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader("res/text/words.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    dictionary.add(line.toLowerCase());
                }
            } catch (IOException e) {
                System.err.println("Error reading dictionary file: " + e.getMessage());
                System.exit(1);
            }
        }

        public void playRound(Map<String, List<String>> playerWords) {
            // Calculate scores for each player
            Map<String, Integer> scores = calculateScores(playerWords);

            // Find round winner(s)
            int maxScore = Collections.max(scores.values());
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                if (entry.getValue() == maxScore) {
                    winners.add(entry.getKey());
                }
            }
            currentRound++;

            // Check if game winner
            if (winners.size() == 1 && currentRound == totalRounds) {
                gameWinner = winners.iterator().next();
                System.out.println("Game Winner: " + gameWinner);
                // You may want to clean up resources or end the game here
            }
        }

        public List<Character> generateRandomLetters() {
            List<Character> randomLetters = new ArrayList<>();
            randomLetters.addAll(generateRandomVowels());
            randomLetters.addAll(generateRandomConsonants());
            Collections.shuffle(randomLetters);
            return randomLetters;
        }

        private List<Character> generateRandomVowels() {
            List<Character> vowels = new ArrayList<>();
            Random random = new Random();
            char[] allVowels = {'a', 'e', 'i', 'o', 'u'};
            for (int i = 0; i < VOWEL_COUNT; i++) {
                vowels.add(allVowels[random.nextInt(allVowels.length)]);
            }
            return vowels;
        }

        private List<Character> generateRandomConsonants() {
            List<Character> consonants = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < CONSONANT_COUNT; i++) {
                char consonant;
                do {
                    consonant = (char) ('a' + random.nextInt(26));
                } while ("aeiou".indexOf(consonant) != -1); // Skip if it's a vowel
                consonants.add(consonant);
            }
            return consonants;
        }

        private boolean canFormWord(String word) {
            List<Character> remainingLetters = new ArrayList<>(randomLetters);
            for (char letter : word.toCharArray()) {
                boolean found = false;
                for (Iterator<Character> iterator = remainingLetters.iterator(); iterator.hasNext(); ) {
                    char currentLetter = iterator.next();
                    if (Character.toLowerCase(currentLetter) == Character.toLowerCase(letter)) {
                        iterator.remove();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }

        private Map<String, Integer> calculateScores(Map<String, List<String>> playerWords) {
            Map<String, Integer> scores = new HashMap<>();
            for (String player : players) {
                int score = calculateScoreForPlayer(player, playerWords);
                scores.put(player, score);
            }
            return scores;
        }

        private int calculateScoreForPlayer(String player, Map<String, List<String>> playerWords) {
            Set<String> uniqueWords = new HashSet<>(playerWords.get(player));
            return uniqueWords.stream().mapToInt(String::length).sum();
        }

        private String createGameId() {
            return UUID.randomUUID().toString();
        }

        private List<Character> getRandomLetters(){
            return this.randomLetters;
        }
    }
}
