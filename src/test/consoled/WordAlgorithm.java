package consoled;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Implement the consoled.WordAlgorithm class
 */
public class WordAlgorithm {
    private static String location = "res/text/words.txt";

    public static void main(String[] args) {
        List<String> vocabulary = readWordsFromFile(location);
        String allowedLetters = "abbot";
        List<String> possibleWords = possibleWords(allowedLetters, vocabulary);
        for (String word : possibleWords) {
            System.out.println(word);
        }
    }

    private static List<String> readWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return words;
    }

    private static List<String> possibleWords(String allowedLetters, List<String> vocabulary) {
        List<String> possibleWords = new ArrayList<>();
        for (String word : vocabulary) {
            if (isWordPossible(word, allowedLetters)) {
                possibleWords.add(word);
            }
        }
        return possibleWords;
    }

    private static boolean isWordPossible(String word, String allowedLetters) {
        StringBuilder remainingLetters = new StringBuilder(allowedLetters);
        for (char letter : word.toCharArray()) {
            int index = remainingLetters.indexOf(String.valueOf(letter));
            if (index == -1) {
                return false;
            } else {
                remainingLetters.deleteCharAt(index);
            }
        }
        return true;
    }
}
