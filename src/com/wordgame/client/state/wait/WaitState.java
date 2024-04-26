package com.wordgame.client.state.wait;

import com.wordgame.client.core.Size;
import com.wordgame.client.game.Game;
import com.wordgame.client.game.time.Timer;
import com.wordgame.client.game.settings.GameSettings;
import com.wordgame.client.state.wait.elements.UIWaitMenu;
import com.wordgame.client.state.wait.elements.UIWaitTime;
import com.wordgame.client.input.Input;
import com.wordgame.client.state.State;
import com.wordgame.client.gui.*;
import com.wordgame.client.gui.text.UIHeader;
import com.wordgame.client.gui.text.UIText;

import java.awt.event.KeyEvent;

public class WaitState extends State {
    private boolean paused;
    private boolean inputEnabled;
    private final UIWaitMenu gameMenu;


    private final Timer gameTimer;

    public WaitState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);


        gameMenu = new UIWaitMenu(input, gameSettings);
        gameTimer = new Timer(10, this::lose);
        inputEnabled = true;
        initializeUI();

        //audioPlayer.playMusic("main.wav");

    }

    private void initializeUI() {
        uiCanvas.addUIComponent(new UIWaitTime());

        UIContainer label = new VerticalContainer();
        label.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        label.addUIComponent(new UIText("WAITING...", 29));
        label.setPadding(new Spacing(100,0,0,0));

        uiCanvas.addUIComponent(label);
    }

    private void lose() {
        inputEnabled = false;
        UIContainer content = new VerticalContainer();
        content.addUIComponent(new UIHeader("NO PLAYERS JOINED", 72));
        gameMenu.setHeaderContent(content);
        toggleMenu(true);

    }

    public void toggleScore(boolean show) {
        if(show) {
            paused = true;
            UIContainer content = new VerticalContainer();
            content.addUIComponent(new UIHeader("SCORE", 72));
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
        if (inputEnabled && !paused && input.isPressed(KeyEvent.VK_F1)) {
            toggleScore(!paused);
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
