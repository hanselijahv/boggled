package com.wordgame.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@SuppressWarnings("SpellCheckingInspection")
public class Test {
    private static final int VOWEL_COUNT = 7;
    private static final int CONSONANT_COUNT = 13;
    private static final int MIN_WORD_LENGTH = 4;
    private static final int GAME_DURATION = 30; // in seconds

    private static List<Character> letters;
    private static final List<String> words = new ArrayList<>();
    private static int totalScore;
    private static Set<String> dictionary;

    public static void main(String[] args) {
        loadDictionary();

        letters = generateRandomLetters();

        System.out.println("Random letters: " + letters);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame();
            }
        }, GAME_DURATION * 1000);


        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Enter your word (minimum 4 letters) or type 'exit' to end the temp.game: ");
            input = scanner.next();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            if (input.length() < MIN_WORD_LENGTH) {
                System.out.println("Word must be at least 4 letters long.");
                continue;
            }

            if (!canFormWord(input)) {
                System.out.println("Word cannot be formed from the given letters.");
                continue;
            }

            if (!isValidWord(input)) {
                System.out.println("Word is not found in the dictionary.");
                continue;
            }

            words.add(input);

            int wordScore = input.length();
            totalScore += wordScore;

            System.out.println("Word Score: " + wordScore);
            System.out.println("Total Score: " + totalScore);
        } while (true);

        System.out.println("Game ended. Total Score: " + totalScore);
        System.out.println("Words entered: " + words);
    }

    public static void loadDictionary() {
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

    private static void endGame() {
        System.out.println("Time's up!");
        System.out.println("Total Score: " + totalScore);
        System.out.println("Words entered: " + words);
        System.exit(0);
    }
}
