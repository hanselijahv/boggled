package main;

import test.Test;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"SpellCheckingInspection", "Duplicates"})
public class UI {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font misterPixel16;
    public int command = 0;


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
    }

    public void drawTitleScreen() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);


        drawCenteredText("Boggled", 96F, gamePanel.tileSize * 3);

        drawMenuOption("New Game", 0);
        drawMenuOption("Leaderboards", 1);
        drawMenuOption("Quit", 2);

    }

    public void drawSettingsState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        drawCenteredText("Settings Screen", 96F, gamePanel.tileSize * 3);
    }

    public void drawLeaderboardsState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        drawCenteredText("Leaderboards", 96F, gamePanel.tileSize * 3);
    }


    public void drawPlayState() {
        graphics2D.setColor(Color.decode("#0d1b2a"));
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        drawCenteredText("Start Screen", 96F, gamePanel.tileSize * 3);

        drawCenteredText("00:30", 48F, gamePanel.tileSize * 5);

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

    public int getXForCenteredText(String text) {
        return (gamePanel.screenWidth - graphics2D.getFontMetrics().stringWidth(text)) / 2;
    }
}


