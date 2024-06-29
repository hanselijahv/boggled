package net.team6.boggled.client.state.ingame;

import BoggledApp.Boggled;
import BoggledApp.Callback;
import BoggledApp.NoWinnerException;
import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.run.Connect;
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.FontUtils;
import net.team6.boggled.utilities.StyledButtonUI;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.StringHolder;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"Duplicates"})
public class InGameState extends JFrame {
    // TODO: tidying
    private final static Boggled boggledImpl = Connect.boggledImpl;
    private final static Callback cref = Connect.cref;
    private final static String playerName = Connect.username;
    private final static String gameID = Connect.boggledImpl.getGameID(cref, playerName);
    private final static String roundRemainingTime = Connect.boggledImpl.getRoundTime(cref, gameID);
    public static List<Character> letters;
    private static int totalScore;
    private final Font font = FontUtils.loadFont("/font/MP16REG.ttf", 68);
    private final Font textFieldFont = FontUtils.loadFont("/font/MP16REG.ttf", 42);
    JLabel titleLabel, submissionDescription;
    String second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    Timer timer;
    private JTextField inputField;


    public InGameState(GameSettings gameSettings) throws IOException, FontFormatException {
        AudioPlayer audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        setTitle("Boggled");
        setUndecorated(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BoggledColors.SYSTEM_COLOR);
        setResizable(false);
        setSize(1290, 800);


        // test TODO: Delete
        System.out.println("boggledImpl: " + boggledImpl.toString());
        System.out.println("cref: " + cref.toString());
        System.out.println("Playername: " + playerName.toString());
        System.out.println("gameID: " + gameID.toString());


        try {
            Thread.sleep(1100);
        } catch (Exception e) {
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

    public static List<Character> getLetters(String gameID) {
        if (boggledImpl == null) {
            throw new IllegalStateException("Boggled Implementation is null");
        }
        if (gameID == null) {
            throw new IllegalArgumentException("Game ID is null");
        }

        System.out.println("TODO TEST gameID: " + gameID);
        String lettersString = boggledImpl.getLetters(gameID);

        System.out.println("TODO TEST letters: " + lettersString);
        List<Character> lettersList = new ArrayList<>();
        for (char c : lettersString.toCharArray()) {
            lettersList.add(c);
        }
        return lettersList;
    }

    public void addUIComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BoggledColors.SYSTEM_COLOR);

        // round number

        JLabel roundLabel = new JLabel("ROUND " + Connect.boggledImpl.currentRound(gameID));
        roundLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        roundLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 35));
        roundLabel.setBorder(new EmptyBorder(20, 30, 0, 0));

        GridBagConstraints topLabelConstraints = new GridBagConstraints();
        topLabelConstraints.gridx = 0;
        topLabelConstraints.gridy = -1;
        topLabelConstraints.weightx = 1.0;
        topLabelConstraints.weighty = 0.1;
        topLabelConstraints.anchor = GridBagConstraints.NORTHWEST;
        topLabelConstraints.fill = GridBagConstraints.HORIZONTAL;

        panel.add(roundLabel, topLabelConstraints);

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        titleLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
        String GAME_DURATION = boggledImpl.getRoundTime(cref, gameID);
        minute = String.valueOf(Integer.parseInt(GAME_DURATION) / 60);
        second = String.valueOf(Integer.parseInt(GAME_DURATION) % 60);

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

        // word submission description

        submissionDescription = new JLabel("Enter Word", SwingConstants.CENTER);
        submissionDescription.setForeground(BoggledColors.PRIMARY_COLOR);
        submissionDescription.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 20));

        GridBagConstraints bottomLabelConstraints = new GridBagConstraints();
        bottomLabelConstraints.gridx = GridBagConstraints.RELATIVE;
        bottomLabelConstraints.gridy = 2;
        bottomLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
        bottomLabelConstraints.weightx = 1.0;
        bottomLabelConstraints.weighty = 0.05;
        bottomLabelConstraints.anchor = GridBagConstraints.CENTER;
        bottomLabelConstraints.fill = GridBagConstraints.HORIZONTAL;

        panel.add(submissionDescription, bottomLabelConstraints);


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
        inputField.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputField.setHorizontalAlignment(JTextField.RIGHT);

        inputField.addActionListener(e -> {
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
            Connect.boggledImpl.submitWord(gameID, playerName, inputText, isWordValid, canForm, response);

            if (response.value.equalsIgnoreCase("Word is invalid!")) {

                submissionDescription.setText(inputText + " is invalid!");
                submissionDescription.setForeground(Color.RED);

            } else if (response.value.equalsIgnoreCase("Word is valid!")) {

                submissionDescription.setText(inputText + " is valid!");
                submissionDescription.setForeground(Color.GREEN);

            }

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

    private boolean isMaxLimitReached(char letter) {
        String input = inputField.getText().toLowerCase();
        long count = input.chars().filter(ch -> ch == letter).count();
        return count >= Collections.frequency(letters, letter);
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

            if (intSecond.get() == -1) {
                intSecond.set(59);
                intMinute[0]--;
                ddSecond = dFormat.format(intSecond);
                ddMinute = dFormat.format(intMinute[0]);
                titleLabel.setText(ddMinute + ":" + ddSecond);
            }

            if (intMinute[0] == 0 && Integer.parseInt(intSecond.toString()) == 0) {
                timer.stop();

                if (boggledImpl.isGameFinished(gameID)) {
                    String gameWinner = boggledImpl.gameWinner(gameID);
                    System.out.println("WINNER FOR GAME:" + gameWinner);

                    getContentPane().removeAll();
                    JPanel overlayPanel = new JPanel();
                    overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
                    overlayPanel.setBackground(new Color(0, 0, 0, 250));
                    JLabel gameWinnerLabel = new JLabel("Game Winner", SwingConstants.CENTER);
                    gameWinnerLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    gameWinnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
                    gameWinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(Box.createVerticalGlue());
                    overlayPanel.add(gameWinnerLabel);

                    JLabel winnerLabel = new JLabel(gameWinner, SwingConstants.CENTER);
                    winnerLabel.setForeground(Color.YELLOW);
                    winnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 68));
                    winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(winnerLabel);

                    JLabel scoreLabel = new JLabel(boggledImpl.getWinnerScore(gameID) + " points!", SwingConstants.CENTER);
                    scoreLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    scoreLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 42));
                    scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(scoreLabel);
                    overlayPanel.add(Box.createVerticalGlue());

                    getContentPane().add(overlayPanel);
                    revalidate();
                } else {
                    String roundWinner;
                try {
                    roundWinner = boggledImpl.roundWinner(gameID);
                    getContentPane().removeAll();
                    JPanel overlayPanel = new JPanel();
                    overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
                    overlayPanel.setBackground(new Color(0, 0, 0, 250));
                    JLabel roundWinnerLabel = new JLabel("Round Winner", SwingConstants.CENTER);
                    roundWinnerLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    roundWinnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
                    roundWinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(Box.createVerticalGlue());
                    overlayPanel.add(roundWinnerLabel);

                    JLabel winnerLabel = new JLabel(roundWinner, SwingConstants.CENTER);
                    winnerLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    winnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 68));
                    winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(winnerLabel);

                    JLabel scoreLabel = new JLabel("YOUR SCORE: " + boggledImpl.roundPoints(gameID, playerName), SwingConstants.CENTER);
                    scoreLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    scoreLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 42));
                    scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(scoreLabel);
                    overlayPanel.add(Box.createVerticalGlue());

                    getContentPane().add(overlayPanel);
                    revalidate();
                } catch (NoWinnerException ex) {
                    getContentPane().removeAll();
                    JPanel overlayPanel = new JPanel();
                    overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
                    overlayPanel.setBackground(new Color(0, 0, 0, 250));
                    JLabel roundWinnerLabel = new JLabel("DRAW", SwingConstants.CENTER);
                    roundWinnerLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    roundWinnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
                    roundWinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    overlayPanel.add(roundWinnerLabel);
                    overlayPanel.add(Box.createVerticalGlue());
                    getContentPane().add(overlayPanel);
                    revalidate();
                }
                Timer delayTimer = new Timer(5000, event -> {
                    getContentPane().removeAll();
                    addUIComponents();
                    revalidate();
                    repaint();
                });
                delayTimer.setRepeats(false); // Make sure the timer only runs once
                delayTimer.start();
            }
        }
        });
        timer.start();
    }

}

