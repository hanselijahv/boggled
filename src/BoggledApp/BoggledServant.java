package BoggledApp;

import net.team6.boggled.common.db.SettingsDAO;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.common.model.Settings;
import org.omg.CORBA.ORB;

import java.sql.SQLException;
import java.util.*;

import static net.team6.boggled.common.db.AccountDAO.accountDAOImpl;
import static net.team6.boggled.common.db.SettingsDAO.settingsDAOImpl;

@SuppressWarnings({"Duplicates", "SpellCheckingInspection"})
public class BoggledServant extends BoggledPOA{
    private ORB orb;
    private final Map<String, String> sessionMap = new HashMap<>();
    public static List<Character> letters;
    private static final int VOWEL_COUNT = 7;
    private static final int CONSONANT_COUNT = 13;
    private static Set<String> dictionary;
    private static int roundTime;
    private static int waitingTime;
    private static int numOfRounds;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }


    @Override
    public synchronized void login(String username, String password) throws UserNotFoundException {
        Account account =  new Account(null, username, password);
        boolean isAuthenticated = false;
        try {
            isAuthenticated = accountDAOImpl.authenticatePlayer(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!isAuthenticated) {
            throw new UserNotFoundException("User not found!");
        }
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, username);
        System.out.println("User '" + username + "' logged in successfully. Session ID: " + sessionId);
    }

    @Override
    public synchronized String getSessionId(String username) {
        for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
            if (entry.getValue().equals(username)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public synchronized void logout(String sessionId) throws NotLoggedInException {
        String username = sessionMap.get(sessionId);
        if (username == null) {
            throw new NotLoggedInException("Session ID '" + sessionId + "' is not valid.");
        }
        sessionMap.remove(sessionId);
        System.out.println("User '" + username + "' logged out successfully.");
    }


    //TODO: IMPLEMENTATION
    @Override
    public String playGame(String username) throws InsufficientPlayersException {
        return null;
    }

    //TODO: IMPLEMENTATION
    @Override
    public void sendWord(String gameID, String word) throws InvalidWordException {

    }

    @Override
    public char[] getLetters(String gameID) {
        letters = generateRandomLetters();
        char[] lettersArray = new char[letters.size()];
        for (int i = 0; i < letters.size(); i++) {
            lettersArray[i] = letters.get(i);
        }
        return lettersArray;
    }

    public static List<Character> generateRandomLetters() {
        List<Character> randomLetters = new ArrayList<>();
        randomLetters.addAll(generateRandomVowels());
        randomLetters.addAll(generateRandomConsonants());
        Collections.shuffle(randomLetters);
        return randomLetters;
    }

    private static List<Character> generateRandomVowels() {
        List<Character> vowels = new ArrayList<>();
        Random random = new Random();
        char[] allVowels = {'a', 'e', 'i', 'o', 'u'};
        for (int i = 0; i < VOWEL_COUNT; i++) {
            vowels.add(allVowels[random.nextInt(allVowels.length)]);
        }
        return vowels;
    }


    private static List<Character> generateRandomConsonants() {
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

    private static boolean canFormWord(String word) {
        List<Character> remainingLetters = new ArrayList<>(letters);
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

    private static boolean isValidWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    //TODO: IMPLEMENTATION
    @Override
    public boolean isGameOver(String gameID) {
        return false;
    }

    //TODO: IMPLEMENTATION
    @Override
    public boolean isRoundOver(String gameID) {
        return false;
    }

    //TODO: IMPLEMENTATION
    @Override
    public RoundWinner getRoundWinner(String gameID) {
        return null;
    }

    //TODO: IMPLEMENTATION
    @Override
    public GameWinner getGameWinner(String gameID) {
        return null;
    }

    //TODO: IMPLEMENTATION
    @Override
    public int getRoundTime() {
        try {
            List<Settings> roundTimeSetting = settingsDAOImpl.getAll();
            Settings setting = roundTimeSetting.get(0);
            roundTime = setting.getRoundTime();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return roundTime;
    }

    //TODO: IMPLEMENTATION
    @Override
    public int getWaitingTime() {
        try {
            List<Settings> waitingTimeSetting = settingsDAOImpl.getAll();
            Settings setting = waitingTimeSetting.get(0);
            waitingTime = setting.getWaitingTime();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return waitingTime;
    }

    @Override
    public int getNumRounds() {
        try {
            List<Settings> numOfRoundsSetting = settingsDAOImpl.getAll();
            Settings setting = numOfRoundsSetting.get(0);
            numOfRounds = setting.getRoundsToWin();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return numOfRounds;
    }

    //TODO: IMPLEMENTATION
    @Override
    public GameWinner[] getLeaderboards() {
        return new GameWinner[0];
    }

    //TODO: IMPLEMENTATION
    @Override
    public GamePlayer[] getGamePlayers(String gameID) {
        return new GamePlayer[0];
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString(); // Generate unique session ID
    }
}
