import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("all")
public class WordGameGUI extends JFrame {

    private static final int GAME_WIDTH = 1366;
    private static final int GAME_HEIGHT = 768;

    public WordGameGUI() {
        super("Word Game");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);

        addGUIComponents();
    }

    public void addGUIComponents() {
        JLabel gameTitle = new JLabel("Word Game", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Verdana", Font.BOLD, 36));
        gameTitle.setBounds((GAME_WIDTH-400)/2,15,400,45);

        add(gameTitle);

        // Test
        JButton startGame = new JButton(loadImage("res/start.png"));
        startGame.setBounds((GAME_WIDTH-225)/2,100,225,225);
        add(startGame, "gapy 50");


    }


    private ImageIcon loadImage(String path) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            return new ImageIcon(bufferedImage);
        } catch (IOException e){
            System.out.println("loadImage method: " + e);
        }

        System.out.println("Could not find image");
        return null;
    }
}
