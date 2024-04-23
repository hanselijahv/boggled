package com.wordgame.boggled.state.game;

import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.game.Game;
import com.wordgame.boggled.game.time.Timer;
import com.wordgame.boggled.game.settings.GameSettings;
import com.wordgame.boggled.state.game.elements.UIGameMenu;
import com.wordgame.boggled.state.game.elements.UIGameTime;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.ui.*;
import com.wordgame.boggled.ui.text.UIHeader;

import java.awt.event.KeyEvent;

public class GameState extends State {
    private boolean paused;
    private boolean inputEnabled;
    private final UIGameMenu gameMenu;


    private final Timer gameTimer;

    public GameState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        gameMenu = new UIGameMenu(input, gameSettings);
        gameTimer = new Timer(30, this::lose);
        inputEnabled = true;
        initializeUI();

        audioPlayer.playMusic("main.wav");

    }

    private void initializeUI() {
        uiCanvas.addUIComponent(new UIGameTime());
    }

    private void lose() {
        inputEnabled = false;
        UIContainer content = new VerticalContainer();
        content.addUIComponent(new UIHeader("ROUND OVER", 72));
        gameMenu.setHeaderContent(content);
        toggleMenu(true);
    }

    public void togglePause(boolean shouldPause) {
        if(shouldPause) {
            paused = true;
            UIContainer content = new VerticalContainer();
            content.addUIComponent(new UIHeader("PAUSED", 72));
            gameMenu.setHeaderContent(content);
            toggleMenu(true);
        } else {
            paused = false;
            toggleMenu(false);
        }
    }

    private void toggleMenu(boolean shouldShowMenu) {
        if(shouldShowMenu && !uiCanvas.hasComponent(gameMenu)) {
            uiCanvas.addUIComponent(gameMenu);
        } else if (!shouldShowMenu) {
            uiCanvas.removeComponent(gameMenu);
        }
    }

    @Override
    protected void handleInput() {
        if (inputEnabled && !paused && input.isPressed(KeyEvent.VK_ESCAPE)) {
            togglePause(!paused);
        }
    }

    @Override
    public void update(Game game) {
        super.update(game);
        if(!paused) {
            gameTimer.update();
            handleInput();
        }
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

}
