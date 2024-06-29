package net.team6.boggled.client.state.ingame;

import BoggledApp.Boggled;
import BoggledApp.Callback;
import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.run.Connect;
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.FontUtils;
import net.team6.boggled.utilities.StyledButtonUI;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.StringHolder;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static net.team6.boggled.run.Connect.username;


@SuppressWarnings({"Duplicates", "SpellCheckingInspection"})
public class InGameState extends JFrame {
    private static int totalScore;
    private static final int VOWEL_COUNT = 7;
    private static final int CONSONANT_COUNT = 13;
    public static List<Character> letters;
    private static Set<String> dictionary;
    private static final List<String> words = new ArrayList<>();
    private static final int MIN_WORD_LENGTH = 4;

    JLabel titleLabel;
    String second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    Timer timer;

    private JTextField inputField;
    private final Font font = FontUtils.loadFont("/font/MP16REG.ttf", 68);
    private final Font textFieldFont = FontUtils.loadFont("/font/MP16REG.ttf", 42);

    // TODO: tidying
    private final static Boggled boggledImpl = Connect.boggledImpl;
    private final static Callback cref = Connect.cref;
    private final static String playerName = username;
    private final static String gameID = Connect.boggledImpl.getGameID(cref, playerName);
    private final static String roundRemainingTime = Connect.boggledImpl.getRoundTime(cref, gameID);


    public InGameState(GameSettings gameSettings) throws IOException, FontFormatException {
        AudioPlayer audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        setTitle("Boggled");
        setUndecorated(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BoggledColors.SYSTEM_COLOR);
        setExtendedState(Frame.MAXIMIZED_BOTH);

        // test TODO: Delete
        System.out.println("boggledImpl: " + boggledImpl.toString());
        System.out.println("cref: " + cref.toString());
        System.out.println("Playername: " + playerName.toString());
        System.out.println("gameID: " + gameID.toString());


        try {
            Thread.sleep(1100);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        addUIComponents();
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                audioPlayer.clear();
            }
        });

        audioPlayer.playMusic("main.wav");


    }



    public void addUIComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BoggledColors.SYSTEM_COLOR);


        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        titleLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
        String GAME_DURATION = boggledImpl.getRoundTime(cref, gameID);
        minute = String.valueOf(Integer.parseInt(GAME_DURATION) / 60);
        second = String.valueOf(Integer.parseInt(GAME_DURATION)  % 60);

        countdownTimer();
        timer.start();

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.weightx = 1.0;
        titleConstraints.weighty = 0.1;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleLabel, titleConstraints);

        JTextField inputField = inputField();


        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(BoggledColors.SYSTEM_COLOR);

        JPanel gridPanel = new JPanel(new GridLayout(2, 10, 5, 5)); // 5 pixel gaps
        gridPanel.setBackground(BoggledColors.SYSTEM_COLOR);

        letters = getLetters(gameID);

        //TODO: delete
        System.out.println("LETTERS: " + letters);

        for (int i = 0; i < 20; i++) {
            JButton button = new JButton(letters.get(i).toString());
            button.setMargin(new Insets(5, 5, 5, 5));
            button.setBackground(BoggledColors.BUTTON_COLOR);
            button.setForeground(BoggledColors.PRIMARY_COLOR);
            button.setPreferredSize(new Dimension(150, 150));
            button.setFont(font);
            button.setUI(new StyledButtonUI());
            addColorChangeListener(button);
            gridPanel.add(button);
        }

        buttonPanel.add(gridPanel);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.weighty = 0.9;
        buttonConstraints.fill = GridBagConstraints.BOTH;

        GridBagConstraints inputFieldConstraints = new GridBagConstraints();
        inputFieldConstraints.gridx = 0;
        inputFieldConstraints.gridy = 2;
        inputFieldConstraints.weightx = 1;
        inputFieldConstraints.weighty = 0.1;
        inputFieldConstraints.insets = new Insets(0, 300, 100, 300);
        inputFieldConstraints.fill = GridBagConstraints.HORIZONTAL;

        panel.add(inputField, inputFieldConstraints);
        panel.add(buttonPanel, buttonConstraints);

        getContentPane().add(panel);
    }

    public JTextField inputField() {

        inputField = new JTextField();
        inputField.setEditable(true);
        inputField.setFont(textFieldFont);
        inputField.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);
        inputField.setForeground(BoggledColors.PRIMARY_COLOR);
        inputField.setBorder( new EmptyBorder(10, 10, 10, 10));
        inputField.setHorizontalAlignment(JTextField.RIGHT);

        inputField.addActionListener(e -> {
            duringGame(returnInputToGame());
            inputField.setText("");
        });

        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                if (!letters.contains(typedChar) || isMaxLimitReached(typedChar)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        inputField.addActionListener(e -> {
            String inputText = inputField.getText();
            BooleanHolder isWordValid = new BooleanHolder();
            BooleanHolder canForm = new BooleanHolder();
            StringHolder response = new StringHolder();
            Connect.boggledImpl.submitWord(gameID, username, inputText, isWordValid, canForm, response);
            System.out.println(response.value);
            System.out.println("Text entered: " + inputText);
            inputField.setText("");
        });

        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                if (!letters.contains(typedChar) || isMaxLimitReached(typedChar)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        return inputField;
    }

    public void duringGame(String input) {
        int wordScore = 0;
        boolean valid = true;

        System.out.println("Random letters: " + letters);
        System.out.println("Enter your word (minimum 4 letters) or type 'exit' to end the temp.game: ");

        if ("exit".equalsIgnoreCase(input)) {
            endGame();
        }

        if (input.length() < MIN_WORD_LENGTH) {
            System.out.println("Word must be at least 4 letters long.");
            valid = false;
        }

        if (!canFormWord(input)) {
            System.out.println("Word cannot be formed from the given letters.");
            valid = false;
        }

        if (!isValidWord(input)) {
            System.out.println("Word is not found in the dictionary.");
            valid = false;
        }

        if (words.contains(input)) {
            System.out.println("Word duplicated not counted");
            valid = false;
        }

        if (valid) {
            words.add(input);
            wordScore = input.length();
            totalScore += wordScore;
        }

        System.out.println("Word Score: " + returnWordScore(wordScore));
        System.out.println("Total Score: " + totalScore);
    }

    private static void endGame() {
        System.out.println("Time's up!");
        System.out.println("Total Score: " + totalScore);
        System.out.println("Words entered: " + words);
        System.exit(0);
    }

    private static int returnWordScore(int wordScore) {
        return wordScore;
    }

    public static List<Character> getLetters(String gameID) {
        if (boggledImpl == null) {
            throw new IllegalStateException("Boggled Implementation is null");
        }
        if (gameID == null) {
            throw new IllegalArgumentException("Game ID is null");
        }

        System.out.println("TEST gameID: " + gameID);
        String lettersString = boggledImpl.getLetters(gameID);

        System.out.println("TEST letters: " + lettersString);
        List<Character> lettersList = new ArrayList<>();
        for (char c : lettersString.toCharArray()) {
            lettersList.add(c);
        }
        return lettersList;
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

    private boolean isMaxLimitReached(char letter) {
        String input = inputField.getText().toLowerCase();
        long count = input.chars().filter(ch -> ch == letter).count();
        return count >= Collections.frequency(letters, letter);
    }

    private String returnInputToGame() {
        return inputField.getText();
    }

    public void addColorChangeListener(JButton button) {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonColor(button);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonColor(button);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonColor(button);
            }
        });
    }


    public void updateButtonColor(JButton button) {
        String input = inputField.getText().toLowerCase();
        char letter = button.getText().charAt(0);
        long count = input.chars().filter(ch -> ch == letter).count();
        if (count == Collections.frequency(letters, letter)) {
            button.setBackground(BoggledColors.BUTTON_PRESSED_COLOR);
            button.setForeground(BoggledColors.DISABLED_COLOR);
        } else {
            button.setBackground(BoggledColors.BUTTON_COLOR);
            button.setForeground(BoggledColors.PRIMARY_COLOR);
        }
    }

    public void countdownTimer() {
        AtomicInteger intSecond = new AtomicInteger(Integer.parseInt(second));
        final int[] intMinute = {Integer.parseInt(minute)};

        timer = new Timer(1000, e -> {

            intSecond.getAndDecrement();
            ddSecond = dFormat.format(intSecond);
            ddMinute = dFormat.format(intMinute[0]);
            titleLabel.setText(ddMinute + ":" + ddSecond);

            if(intSecond.get() == -1) {
                intSecond.set(59);
                intMinute[0]--;
                ddSecond = dFormat.format(intSecond);
                ddMinute = dFormat.format(intMinute[0]);
                titleLabel.setText(ddMinute + ":" + ddSecond);
            }
            if(intMinute[0] == 0 && Integer.parseInt(intSecond.toString()) == 0) {
                timer.stop();
                
                Timer delayTimer = new Timer(5000, event -> {
                    getContentPane().removeAll();
                    addUIComponents();
                    revalidate();
                    repaint();
                });
                delayTimer.setRepeats(false); // Make sure the timer only runs once
                delayTimer.start();
            }
        });
        timer.start();
    }

}

