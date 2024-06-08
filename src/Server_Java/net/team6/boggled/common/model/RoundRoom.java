package Server_Java.net.team6.boggled.common.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RoundRoom {
	GameRoom gameRoom;

	private final ConcurrentHashMap<String, Set<String>> playerWords = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Integer> roundScores = new ConcurrentHashMap<>();

	private static final int VOWEL_COUNT = 7;
	private static final int CONSONANT_COUNT = 13;
	private final List<Character> randomLetters;
	private final List<String> players;
	private Set<String> dictionary;
	private String roundWinner;

	public RoundRoom(GameRoom gameRoom, List<String> players) {
		this.gameRoom = gameRoom;
		this.players = players;
		loadDictionary();
		randomLetters = generateRandomLetters();
	}

	public void submitWord(String playerName, String word) {
		if (!playerWords.containsKey(playerName)) {
			playerWords.put(playerName, new HashSet<>());
		}
		if (canFormWord(word) && isValidWord(word)) {
			playerWords.get(playerName).add(word);
			System.out.println(playerName + " submitted word: " + word);
		} else {
			System.out.println("Invalid word submission by " + playerName + ": " + word);
		}
	}

	public void endRound() {
		Map<String, Integer> scores = calculateScores();
		this.roundWinner = null;
		int maxScore = 0;
		List<String> maxScorePlayers = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : scores.entrySet()) {
			String player = entry.getKey();
			int score = entry.getValue();
			roundScores.put(player, score);
			if (score > maxScore) {
				maxScore = score;
				maxScorePlayers.clear();
				maxScorePlayers.add(player);
			} else if (score == maxScore) {
				maxScorePlayers.add(player);
			}
		}
		if (maxScorePlayers.size() > 1) {
			System.out.println("There is a tie for this round. No winner.");
		} else if (maxScorePlayers.size() == 1) {
			this.roundWinner = maxScorePlayers.get(0);
			System.out.println("The winner for this round is " + this.roundWinner);
			gameRoom.getPlayerStandings().put(this.roundWinner, gameRoom.getPlayerStandings().getOrDefault(this.roundWinner, 0) + 1);
			gameRoom.getPlayerScores().put(this.roundWinner, gameRoom.getPlayerScores().getOrDefault(this.roundWinner, 0) + maxScore);
		} else {
			System.out.println("No winner for this round");
		}
		printRoundScores();
	}

	public void printRoundScores() {
		System.out.println("Current scores:");
		for (Map.Entry<String, Integer> entry : roundScores.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
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

	public boolean canFormWord(String word) {
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

	private boolean isValidWord(String word) {
		return dictionary.contains(word.toLowerCase());
	}

	private Map<String, Integer> calculateScores() {
		Map<String, Integer> scores = new HashMap<>();
		for (String player : players) {
			Set<String> playerWordsSet = playerWords.get(player);
			Set<String> uniqueWords = playerWordsSet != null ? new HashSet<>(playerWordsSet) : new HashSet<>();
			for (Map.Entry<String, Set<String>> entry : playerWords.entrySet()) {
				if (!entry.getKey().equals(player)) {
					uniqueWords.removeAll(entry.getValue());
				}
			}
			int score = uniqueWords.stream().mapToInt(String::length).sum();
			scores.put(player, score);
		}
		return scores;
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

	public ConcurrentHashMap<String, Set<String>> getPlayerWords() {
		return playerWords;
	}

	public List<String> getPlayers() {
		return players;
	}

	public Set<String> getDictionary() {
		return dictionary;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public ConcurrentHashMap<String, Integer> getRoundScores() {
		return roundScores;
	}

	public List<Character> getRandomLetters() {
		return randomLetters;
	}

	public String getRoundWinner() {
		return roundWinner;
	}
}