package net.team6.boggled.client.state.waiting;

import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.time.Timer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.gui.container.HorizontalContainer;
import net.team6.boggled.client.gui.container.UIContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.waiting.elements.UIWaitingMenu;
import net.team6.boggled.client.state.waiting.elements.UIWaitingTimer;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.gui.text.UIHeader;
import net.team6.boggled.client.gui.text.UIText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class WaitingState extends State {
    private boolean paused;
    private boolean inputEnabled;
    private final UIWaitingMenu gameMenu;


    private final Timer gameTimer;

    public WaitingState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        gameMenu = new UIWaitingMenu(input, gameSettings);
        gameTimer = new Timer(10, this::lose);
        inputEnabled = true;
        initializeUI();

        audioPlayer.playMusic("main.wav");

    }

    private void initializeUI() {
        UIContainer timer = new HorizontalContainer();
        timer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        timer.addUIComponent(new UIWaitingTimer());

        UIContainer label = new VerticalContainer();
        label.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        label.addUIComponent(new UIText("WAITING...", 29));
        label.setPadding(new Spacing(100,0,0,0));


        UIContainer text = new VerticalContainer();
        text.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        text.setBackgroundColor(Color.WHITE);
        text.setPadding(new Spacing(200,70));

        uiCanvas.addUIComponent(text);
        uiCanvas.addUIComponent(timer);
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
    public void update(Game game) throws SQLException {
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
