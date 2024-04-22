package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean up, down, left, right;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState) {
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.command--;
                gamePanel.playSoundEffect(1);
                if (gamePanel.ui.command < 0) {
                    gamePanel.ui.command = 3;

                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.command++;
                gamePanel.playSoundEffect(1);
                if (gamePanel.ui.command > 3) {
                    gamePanel.ui.command = 0;

                }
            }

            if (code == KeyEvent.VK_ENTER) {
                gamePanel.playSoundEffect(2);

                if (gamePanel.ui.command == 0) {
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.command == 1) {
                    gamePanel.gameState = gamePanel.leaderboardsState;
                }

                if (gamePanel.ui.command == 2) {
                    gamePanel.gameState = gamePanel.settingsState;
                }



                if (gamePanel.ui.command == 3) {
                    System.exit(0);
                }
            }
        }

        if (gamePanel.gameState == gamePanel.settingsState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }

        if (gamePanel.gameState == gamePanel.leaderboardsState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }

        if (gamePanel.gameState == gamePanel.playState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

