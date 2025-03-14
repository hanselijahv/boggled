package net.team6.boggled.client.state.ingame;

import BoggledApp.Boggled;
import BoggledApp.Callback;
import BoggledApp.NoWinnerException;
import BoggledApp.NotLoggedInException;
import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.state.menu.elements.BoggledMainMenu;
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
    // Constants
    private final Font font = FontUtils.loadFont("/font/MP16REG.ttf", 68);
    private final Font textFieldFont = FontUtils.loadFont("/font/MP16REG.ttf", 42);
    // Static fields
    private static final Boggled boggledImpl = Connect.boggledImpl;
    // Instance fields
    private final Callback cref = Connect.cref;
    private final String playerName = Connect.username;
    private final String gameID = boggledImpl.getGameID(cref, playerName);
	// UI Components
    private JLabel titleLabel;
    private JLabel submissionDescription;
    private JTextField inputField;
    private JDialog dialog;
    private AudioPlayer audioPlayer;
    private BoggledMainMenu boggledMainMenu;
    private KeyEventDispatcher keyEventDispatcher;
    // Model
    public DefaultListModel<String> listModel = new DefaultListModel<>();
    public List<Character> letters;
    // Timer related fields
    private Timer timer;
    private String second, minute;
    private String ddSecond, ddMinute;
    private final DecimalFormat dFormat = new DecimalFormat("00");
    // State
    private boolean dialogVisible;

    public InGameState(GameSettings gameSettings, BoggledMainMenu boggledMainMenu) throws IOException, FontFormatException {
        this.boggledMainMenu = boggledMainMenu;
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        setTitle("Boggled");
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(BoggledColors.SYSTEM_COLOR);
        setResizable(false);
        setSize(1920, 1080);

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
				boggledImpl.logout(playerName);
			} catch (NotLoggedInException ex) {
				throw new RuntimeException(ex);
			}
		}
        });



        keyEventDispatcher = e -> {
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
	  };

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

        audioPlayer.playMusic("main.wav");

        // disable main menu buttons when ingame
        boggledMainMenu.getPlayGameB().setEnabled(false);
        boggledMainMenu.getLeaderboardB().setEnabled(false);
        boggledMainMenu.getOptionsB().setEnabled(false);
        boggledMainMenu.getLogoutB().setEnabled(false);

    }

    private void showCustomDialog() {
        if (dialog == null) {
            String playersScores = boggledImpl.gameScore(gameID);
            final String maxScore = String.valueOf(boggledImpl.totalRounds(gameID));

            String[] rows = playersScores.split("\n");

            String[] listData = new String[rows.length + 2];
            listData[0] = "Game Score";
            listData[1] = "";

            for (int i = 0; i < rows.length; i++) {
                listData[i + 2] = rows[i] + maxScore;
            }

            JList<String> list = new JList<>(listData);
            list.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 30));
            list.setForeground(Color.WHITE);
            list.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);

            // custom JDialog
            dialog = new JDialog(this, Dialog.ModalityType.MODELESS);
            dialog.setUndecorated(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setResizable(false);
            dialog.setEnabled(false);
            dialog.getContentPane().setBackground(BoggledColors.MENU_BACKGROUND_COLOR);

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
            scrollPane.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);

            // customize scrollbar
            scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
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

            dialog.add(scrollPane);

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

        String lettersString = boggledImpl.getLetters(gameID);
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

        JLabel roundLabel = new JLabel("ROUND " + boggledImpl.currentRound(gameID));
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

        // username display

        JLabel newLabel = new JLabel("» " + playerName);
        newLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        newLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 20));
        newLabel.setBorder(new EmptyBorder(0, 30, 20, 0));

        GridBagConstraints newLabelConstraints = new GridBagConstraints();
        newLabelConstraints.gridx = 0;
        newLabelConstraints.gridy = 1;
        newLabelConstraints.weightx = 1.0;
        newLabelConstraints.weighty = 0.1;
        newLabelConstraints.anchor = GridBagConstraints.NORTHWEST;
        newLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
        //        newLabelConstraints.insets = new Insets(10, 0, 0, 0);

        panel.add(newLabel, newLabelConstraints);

        // words label
	    JLabel wordsLabel = new JLabel("Words Submitted");
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
        topRightLabelConstraints.insets = new Insets(0, 0, 120, 0);

        panel.add(wordsLabel, topRightLabelConstraints);

        // valid submitted words
        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));
        smallPanel.setPreferredSize(new Dimension(100, 100));

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
        topRightConstraints.insets = new Insets(35, 0, 0, 40);
        panel.add(scrollPane, topRightConstraints);

        // timer display

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(BoggledColors.PRIMARY_COLOR);
        titleLabel.setFont(FontUtils.loadFont("/font/MP16REG.ttf", 100));
	    String gameDuration = boggledImpl.getRoundTime(cref, gameID);
        minute = String.valueOf(Integer.parseInt(gameDuration) / 60);
        second = String.valueOf(Integer.parseInt(gameDuration) % 60);

        countdownTimer();
        timer.start();
        //startRemainingTimeChecker();

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

        System.out.println("LETTERS: " + letters);  // debug

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

        inputField.addActionListener(e -> inputField.setText(""));

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
            boggledImpl.submitWord(gameID, playerName, inputText, isWordValid, canForm, response);

            if (response.value.equalsIgnoreCase("Word is invalid!")) {

                submissionDescription.setText(inputText + " is invalid!");
                submissionDescription.setForeground(Color.RED);

            } else if (listModel.contains(inputText)) {

                submissionDescription.setText(inputText + " is already submitted!");
                submissionDescription.setForeground(Color.YELLOW);

            } else if (response.value.equalsIgnoreCase("Word is valid!")) {

                submissionDescription.setText(inputText + " is valid!");
                submissionDescription.setForeground(Color.GREEN);

                if (!listModel.contains(inputText)) {
                    listModel.addElement(inputText);
                    repaint();
                    revalidate();
                }
            }

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
                        this.dispose();
                        audioPlayer.clear();

                        boggledMainMenu.getPlayGameB().setEnabled(true);
                        boggledMainMenu.getLeaderboardB().setEnabled(true);
                        boggledMainMenu.getOptionsB().setEnabled(true);
                        boggledMainMenu.getLogoutB().setEnabled(true);

                        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);

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

