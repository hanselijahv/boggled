package main;


import java.awt.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static test.Test.generateRandomLetters;
import static test.Test.loadDictionary;

@SuppressWarnings({"Duplicates"})
public class UI {


    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font misterPixel16;
    public int command = 0;
    public boolean lettersPrinted = false;
    private long remainingMillis;
    List<Character> letters;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/MP16REG.ttf");
            misterPixel16 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
        } catch (Exception e) {
            System.out.println("Font creation failed: " + e.getMessage());
        }
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(misterPixel16);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }

        if (gamePanel.gameState == gamePanel.settingsState) {
            drawSettingsState();
        }

        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayState();
        }

        if (gamePanel.gameState == gamePanel.leaderboardsState) {
            drawLeaderboardsState();
        }

        if (gamePanel.gameState == gamePanel.tabState){
            drawTabState();
        }

        if (gamePanel.gameState == gamePanel.pauseState){
            drawPauseScreen();
        }

    }

    public void drawTitleScreen() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);


        drawCenteredText("Boggled", 96F, gamePanel.tileSize * 3);

        drawMenuOption("New Game", 0);
        drawMenuOption("Leaderboards", 1);
        drawMenuOption("Settings", 2);
        drawMenuOption("Quit", 3);

    }

    public void drawSettingsState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        drawCenteredText("Settings", 96F, gamePanel.tileSize * 3);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int y = gamePanel.screenHeight / 2;
        drawCenteredText(text, 96F, y);
    }

    public void drawLeaderboardsState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        drawCenteredText("Leaderboards", 96F, gamePanel.tileSize * 3);
    }


    public void drawPlayState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        drawCenteredText("Start", 96F, gamePanel.tileSize * 3);

        if (!lettersPrinted) {
            loadDictionary();
            letters = generateRandomLetters();
            System.out.println("Random letters: " + letters);
            lettersPrinted = true;  // Set the flag to true after printing
            startCountdownTimer();
        }

        drawLetters();
        drawCountdownTimer();
    }

    private void drawLetters() {
        int rectWidth = 50;
        int rectHeight = 50;
        int paddingX = 10; // Padding between columns
        int paddingY = 10; // Padding between rows
        int totalWidth = 10 * rectWidth + 9 * paddingX;
        int totalHeight = 2 * rectHeight + paddingY;
        int startX = (gamePanel.screenWidth - totalWidth) / 2;
        int startY = (gamePanel.screenHeight - totalHeight) / 2;
        int fontSize = rectWidth * 2 / 3;

        graphics2D.setFont(graphics2D.getFont().deriveFont((float) fontSize));

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 10; col++) {
                int index = row * 10 + col;
                if (index >= letters.size()) break; // Break if all letters are drawn
                char letter = letters.get(index);
                int rectX = startX + col * (rectWidth + paddingX);
                int rectY = startY + row * (rectHeight + paddingY);
                graphics2D.setColor(Color.WHITE);
                graphics2D.fillRect(rectX, rectY, rectWidth, rectHeight);
                graphics2D.setColor(Color.decode("#0d1b2a"));
                int textX = rectX + (rectWidth - graphics2D.getFontMetrics().stringWidth(String.valueOf(letter))) / 2;
                int textY = rectY + (rectHeight + graphics2D.getFontMetrics().getAscent()) / 2;
                graphics2D.drawString(String.valueOf(letter), textX, textY);
            }
        }
    }


    private void startCountdownTimer() {

        int countdownSeconds = 30;

        remainingMillis = countdownSeconds * 1000;


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                remainingMillis -= 1000;

                if (remainingMillis <= 0) {
                    timer.cancel();

                }
                gamePanel.repaint();
            }
        }, 0, 1000);
    }

    private void drawCountdownTimer() {
        long remainingSeconds = remainingMillis / 1000;

        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;

        String timerText = String.format("%02d:%02d", minutes, seconds);

        drawCenteredText(timerText, 48F, gamePanel.tileSize * 5);
    }

    private void drawCenteredText(String text, float fontSize, int y) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, fontSize));
        int x = getXForCenteredText(text);

        graphics2D.setColor(Color.decode("#778da9"));
        graphics2D.drawString(text, x + 2, y + 2);

        graphics2D.setColor(Color.decode("#e0e1dd"));
        graphics2D.drawString(text, x, y);
    }

    private void drawMenuOption(String text, int index) {

        int y = gamePanel.tileSize * (9 + index);
        drawCenteredText(text, (float) 48.0, y);

        if (command == index) {
            graphics2D.drawString(">", getXForCenteredText(text) - gamePanel.tileSize, y);
        }
    }

    private void drawTabState(){
        // Calculate the dimensions for the submenu
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*5;

        // Draw the submenu
        drawSubMenu(x, y, width, height);
    }

    private void drawSubMenu(int x, int y, int width, int height) {
        // Set the color with an alpha value for transparency
        Color c = new Color(27, 27, 27, 70);

        graphics2D.setColor((Color.WHITE));
        graphics2D.fillRect(x, y, width, height);
        graphics2D.drawRect(x, y, width, height);
    }

    public int getXForCenteredText(String text) {
        return (gamePanel.screenWidth - graphics2D.getFontMetrics().stringWidth(text)) / 2;
    }
}


