package net.team6.boggled.client.state.ingame;

import BoggledApp.Boggled;
import BoggledApp.Callback;
import BoggledApp.NoWinnerException;
import BoggledApp.NotLoggedInException;
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
import javax.swing.plaf.basic.BasicScrollBarUI;
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
    private static String gameDuration =  Connect.boggledImpl.getRoundTime(cref, gameID);
//    private final static String roundRemainingTime = Connect.boggledImpl.getRoundTime(cref, gameID);
    String second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    public static List<Character> letters;
    public static DefaultListModel<String> listModel = new DefaultListModel<>();
    private final Font font = FontUtils.loadFont("/font/MP16REG.ttf", 68);
    private final Font textFieldFont = FontUtils.loadFont("/font/MP16REG.ttf", 42);

    JLabel titleLabel, submissionDescription, wordsLabel, gameScoreLabel;
    Timer timer;
    private JTextField inputField;
    private JDialog dialog;
    private boolean dialogVisible;

    public InGameState(GameSettings gameSettings) throws IOException, FontFormatException {
        AudioPlayer audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        setTitle("Boggled");
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(BoggledColors.SYSTEM_COLOR);
        setResizable(false);
        setSize(1290, 800);

        // test TODO: Delete
        System.out.println("boggledImpl: " + boggledImpl.toString());
        System.out.println("cref: " + cref.toString());
        System.out.println("Playername: " + playerName.toString());
        System.out.println("gameID: " + gameID.toString());
        System.out.println("GAME_DURATION: " + gameDuration);

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
			try {
				Connect.boggledImpl.logout(playerName);
			} catch (NotLoggedInException ex) {
				throw new RuntimeException(ex);
			}
		}
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (!dialogVisible) {
                        showCustomDialog();
                        dialogVisible = true;
                    }
                } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (dialogVisible) {
                        closeCustomDialog();
                        dialogVisible = false;
                    }
                }
                return false;
            }
        });

        audioPlayer.playMusic("main.wav");

    }

    private void showCustomDialog() {
        if (dialog == null) {
            String playersScores = boggledImpl.gameScore(gameID);
            System.out.println(playersScores);

            playersScores = playersScores.replace("\n", "<br>");

            JLabel gameScoreLabel = new JLabel("Game Score:" + playersScores);
            gameScoreLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 30));
            gameScoreLabel.setForeground(Color.WHITE);
            gameScoreLabel.setBorder(new EmptyBorder(50, 50, 50, 50));

            // custom JDialog
            dialog = new JDialog(this, Dialog.ModalityType.MODELESS);
            dialog.setUndecorated(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setResizable(false);

            JPanel contentPane = new JPanel(new BorderLayout());
            contentPane.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);
            contentPane.add(gameScoreLabel, BorderLayout.CENTER);

            dialog.setContentPane(contentPane);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }


    private void closeCustomDialog() {
        if (dialog != null) {
            dialog.dispose();
            dialog = null;
        }
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

        // words label
        wordsLabel = new JLabel("Words Submitted");
        wordsLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        wordsLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 12));
        wordsLabel.setBorder(new EmptyBorder(0, 0, 0, 0));

        GridBagConstraints topRightLabelConstraints = new GridBagConstraints();
        topRightLabelConstraints.gridx = 1;
        topRightLabelConstraints.gridy = 0;
        topRightLabelConstraints.weightx = 0.1;
        topRightLabelConstraints.weighty = 0.1;
        topRightLabelConstraints.anchor = GridBagConstraints.NORTHEAST;
        topRightLabelConstraints.fill = GridBagConstraints.BOTH;
        topRightLabelConstraints.insets = new Insets(0, 0, 60, 0);

        panel.add(wordsLabel, topRightLabelConstraints);

        // valid submitted words
        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));
        smallPanel.setPreferredSize(new Dimension(100, 100));

//        listItems = new ArrayList<>();
//        JList<String> itemList = new JList<>(listItems.toArray(new String[0]));
        JList<String> itemList = new JList<>(listModel);
        itemList.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);
        itemList.setForeground(BoggledColors.PRIMARY_COLOR);
        itemList.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 12));

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(100, 100));

        // customize scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = BoggledColors.PRIMARY_COLOR;
                this.trackColor = BoggledColors.MENU_BACKGROUND_COLOR;
                this.thumbColor = Color.GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        GridBagConstraints topRightConstraints = new GridBagConstraints();
        topRightConstraints.gridx = 1;
        topRightConstraints.gridy = 0;
        topRightConstraints.weightx = 0.1;
        topRightConstraints.weighty = 0.1;
        topRightConstraints.anchor = GridBagConstraints.NORTHEAST;
        topRightConstraints.fill = GridBagConstraints.BOTH;
        topRightConstraints.insets = new Insets(50, 0, 0, 40);
        panel.add(scrollPane, topRightConstraints);

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

        // game score

//        String playersScores = boggledImpl.gameScore(gameID);
//        System.out.println("GAME SCORE: " +  playersScores);
//
//        JLabel gameScoreLabel = new JLabel("<html>Game Score: <br><br>" + playersScores.replace("\n", "<br>") + "</html>", SwingConstants.LEFT);
//        gameScoreLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 20));
//        gameScoreLabel.setForeground(BoggledColors.PRIMARY_COLOR);
//        gameScoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        GridBagConstraints gameScoreLabelConstraints = new GridBagConstraints();
//        gameScoreLabelConstraints.gridx = 0;
//        gameScoreLabelConstraints.gridy = 1;
//        gameScoreLabelConstraints.weightx = 1.0;
//        gameScoreLabelConstraints.weighty = 0.1;
//        gameScoreLabelConstraints.fill = GridBagConstraints.NONE;
//        gameScoreLabelConstraints.anchor = GridBagConstraints.WEST;
//        gameScoreLabelConstraints.insets = new Insets(0, 30, 0, 0);
//
//        panel.add(gameScoreLabel, gameScoreLabelConstraints);

        // title

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        titleLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
        gameDuration = boggledImpl.getRoundTime(cref, gameID);
        minute = String.valueOf(Integer.parseInt(gameDuration) / 60);
        second = String.valueOf(Integer.parseInt(gameDuration) % 60);

        countdownTimer();
        timer.start();

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = GridBagConstraints.REMAINDER;
        titleConstraints.weightx = 1.0;
        titleConstraints.weighty = 0.1;
        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;

        panel.add(titleLabel, titleConstraints);

        JTextField inputField = inputField();

        // word submission description

        submissionDescription = new JLabel("Enter Word", SwingConstants.CENTER);
        submissionDescription.setForeground(BoggledColors.PRIMARY_COLOR);
        submissionDescription.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 20));

        GridBagConstraints bottomLabelConstraints = new GridBagConstraints();
        bottomLabelConstraints.gridx = GridBagConstraints.RELATIVE;
        bottomLabelConstraints.gridy = 3;
        bottomLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
        bottomLabelConstraints.weightx = 1.0;
        bottomLabelConstraints.weighty = 0.05;
        bottomLabelConstraints.anchor = GridBagConstraints.CENTER;
        bottomLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
        bottomLabelConstraints.insets = new Insets(0, 300, 100, 300);

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
        buttonConstraints.gridwidth = GridBagConstraints.REMAINDER;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.weighty = 0.9;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints inputFieldConstraints = new GridBagConstraints();
        inputFieldConstraints.gridx = 0;
        inputFieldConstraints.gridy = 2;
        inputFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        inputFieldConstraints.weightx = 1;
        inputFieldConstraints.weighty = 0.1;
        inputFieldConstraints.anchor = GridBagConstraints.CENTER;
        inputFieldConstraints.insets = new Insets(0, 300, 0, 300);
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
        inputField.setHorizontalAlignment(JTextField.CENTER);

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

                if (!listModel.contains(inputText)) {
                    listModel.addElement(inputText);
                    System.out.println(listModel);
                    repaint();
                    revalidate();
                }
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

                    JButton menuButton = new JButton("Back to Menu");
                    menuButton.setForeground(BoggledColors.PRIMARY_COLOR);
                    menuButton.setBackground(BoggledColors.BUTTON_COLOR);
                    menuButton.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 30));
                    menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    menuButton.setUI(new StyledButtonUI());

                    menuButton.addActionListener(a -> {
                        // TODO: go to menu
                    });

                    overlayPanel.add(Box.createVerticalGlue());
                    overlayPanel.add(menuButton);
                    overlayPanel.add(Box.createVerticalStrut(40));

                    getContentPane().add(overlayPanel);
                    revalidate();
                } else {
                    String roundWinner;
                    listModel.clear();
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
                    overlayPanel.add(Box.createVerticalGlue());

                    JLabel roundWinnerLabel = new JLabel("DRAW", SwingConstants.CENTER);
                    roundWinnerLabel.setForeground(BoggledColors.PRIMARY_COLOR);
                    roundWinnerLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
                    roundWinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    overlayPanel.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.CENTER;
                    overlayPanel.add(roundWinnerLabel, gbc);

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

