package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class WordGame extends JFrame {


    public WordGame() {
        super("Word Game");

        GamePanel gamePanel = new GamePanel();

        setResizable(false);
        add(gamePanel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        gamePanel.startGameThread();
        gamePanel.setUpGame();
    }


    private ImageIcon loadImage(String path) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            System.out.println("loadImage method: " + e);
        }

        System.out.println("Could not find image");
        return null;
    }
}
