package test.game;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class View extends JFrame {

    private static int totalScore;
    private static final int VOWEL_COUNT = 7;
    private static final int CONSONANT_COUNT = 13;
    private static Font calculatorFont;
    private static List<Character> letters;
    private static Set<String> dictionary;
    private static final List<String> words = new ArrayList<>();
    private static Map<Character, Integer> letterOccurrences;
    private static final int MIN_WORD_LENGTH = 4;
    private static final int GAME_DURATION = 120;
    private static char deletedChar;
    private ImageTextField inputField;

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

    public static void main(String[] args) {
        try {
            View view = new View();
            view.createJFrame();

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

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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

        System.out.println("Word Score: " + wordScore);
        System.out.println("Total Score: " + totalScore);
    }

    public void createJFrame() throws IOException, FontFormatException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Boggled");
        ImageIcon icoImage = createScaledImageIcon("res/img/icon.png");
        jFrame.setIconImage(icoImage.getImage());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(contentPane());
        jFrame.pack();
        jFrame.setMinimumSize(new Dimension(311, 431));
        jFrame.setResizable(false);
        jFrame.setSize(311, 431);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public JPanel contentPane() throws IOException, FontFormatException {
        JPanel contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        letters = generateRandomLetters();
        JTextField inputField = inputField();
        JPanel buttonPanel = buttonPanel();
        contentPane.add(inputField, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        return contentPane;
    }

    public JTextField inputField() throws IOException, FontFormatException {
        ImageIcon javaImage = createScaledImageIcon("res/img/star.png");
        inputField = new ImageTextField(javaImage);
        inputField.setEditable(true);
        InputStream is = getClass().getResourceAsStream("res/fonts/MP16REG.ttf");
        calculatorFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(30f);
        inputField.setFont(calculatorFont);
        inputField.setBackground(new Color(212, 226, 227));
        inputField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
        inputField.setHorizontalAlignment(JTextField.RIGHT);

        // NEW
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                duringGame(handleInputFieldEnter());
                inputField.setText("");
            }
        });

        // TODO listener for backspace
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String text = inputField.getText();
                    if (text.length() > 0) {
                        deletedChar = text.charAt(text.length() - 1);
                    }
                }
            }
        });

        return inputField;
    }

    // NEW
    private String handleInputFieldEnter() {
        String inputText = inputField.getText();
        return inputText;
    }

    public JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setPreferredSize(new Dimension(325, 300));
        addButtonsToPanel(buttonPanel);
        return buttonPanel;
    }

//    public void addButtonsToPanel(JPanel buttonPanel) {
//        letters = generateRandomLetters();
//        for (Character letter : letters) {
//            JButton button = new JButton(letter.toString());
//            button.setPreferredSize(new Dimension(55, 55));
//            button.setFont(calculatorFont);
//            button.setBackground(new Color(213, 215, 216));
//            button.setUI(new StyledButtonUI());
//            buttonPanel.add(button);
//            addColorChangeListener(button);
//        }
//    }

    public void addButtonsToPanel(JPanel buttonPanel) {
        Map<Character, Integer> letterOccurrences = new HashMap<>();
        letters.forEach(letter -> letterOccurrences.put(letter, letterOccurrences.getOrDefault(letter, 0) + 1));
        for (Character letter : letters) {
            JButton button = new JButton(letter.toString());
            button.addActionListener(e -> {
                addColorChangeListener(button);
                //if (letterOccurrences.get(letter) > 0) {
                inputField.setText(inputField.getText() + letter);
                letterOccurrences.put(letter, letterOccurrences.get(letter) - 1);
                disableButton(buttonPanel, letterOccurrences);
                //}
            });
            button.setPreferredSize(new Dimension(55, 55));
            button.setFont(calculatorFont);
            button.setBackground(new Color(213, 215, 216));
            button.setUI(new StyledButtonUI());
            buttonPanel.add(button);
        }
    }

    private void disableButton(JPanel buttonPanel, Map<Character, Integer> letterOccurrences) {
        for (Component component : buttonPanel.getComponents()) {
            JButton button = (JButton) component;
            if (component instanceof JButton) {
                button = (JButton) component;
                char letter = button.getText().charAt(0);
                button.setEnabled(letterOccurrences.get(letter) > 0);
            }
        }
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
        String input = inputField.getText();
        if (input.contains(button.getText())) {
            button.setBackground(new Color(90, 171, 93));
        } else {
            button.setBackground(new Color(213, 215, 216));
            button.setEnabled(true);
        }
    }

    public ImageIcon createScaledImageIcon(String path) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        Image scaledImg = image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}

class ImageTextField extends JTextField {
    private ImageIcon imageIcon;

    public ImageTextField(ImageIcon icon) {
        super();
        this.imageIcon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageX = 5;
        int imageY = (getHeight() - imageIcon.getIconHeight()) / 2;
        imageIcon.paintIcon(this, g, imageX, imageY);
    }

    public void setImage(ImageIcon icon) {
        this.imageIcon = icon;
    }
}

class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (c instanceof JToggleButton) {
            yOffset += 2;
        }

        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}
