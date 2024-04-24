package game;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class View extends JFrame {


    private static int totalScore;
    private static final int VOWEL_COUNT = 7;
    private static final int CONSONANT_COUNT = 13;
    private static Font calculatorFont;
    private static List<Character> letters;

    private ImageTextField inputField;

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

    public static void main(String[] args) {
        try {
            View view = new View();
            view.createJFrame();

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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
        return inputField;
    }


    public JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setPreferredSize(new Dimension(325, 300));
        addButtonsToPanel(buttonPanel);
        return buttonPanel;
    }

    public void addButtonsToPanel(JPanel buttonPanel) {
        letters = generateRandomLetters();
        for (Character letter : letters) {
            JButton button = new JButton(letter.toString());
            button.setPreferredSize(new Dimension(55, 55));
            button.setFont(calculatorFont);
            button.setBackground(new Color(213, 215, 216));
            button.setUI(new StyledButtonUI());
            buttonPanel.add(button);
            addColorChangeListener(button);
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
        }
    }


    public ImageIcon createScaledImageIcon(String path) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        Image scaledImg = image.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
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
