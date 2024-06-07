package net.team6.boggled.client.state.waiting;

import net.team6.boggled.client.state.ingame.InGameState;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.time.Timer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.gui.container.HorizontalContainer;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.waiting.elements.BoggledWaitingMenu;
import net.team6.boggled.client.state.waiting.elements.BoggledWaitingTimer;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.run.Connect;
import net.team6.boggled.server.dev.settings.ServerSettings;
import net.team6.boggled.utilities.BoggledColors;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

public class WaitingState extends State {
    private boolean showScore;
    private boolean inputEnabled;
    private final BoggledWaitingMenu gameMenu;
    private Timer gameTimer;

    public WaitingState(Size windowSize, Input input, GameSettings gameSettings) throws SQLException {
        super(windowSize, input, gameSettings);

        ServerSettings serverSettings = new ServerSettings(false);
        gameMenu = new BoggledWaitingMenu(input, gameSettings);
        inputEnabled = true;
        gameTimer = new Timer(Double.parseDouble(Connect.boggledImpl.getWaitingTime(Connect.cref)), this::endWaiting);
        initializeUI();

        boolean isReadyToStart = Connect.boggledImpl.isGameReadyToStart();
        System.out.println(isReadyToStart);

        //audioPlayer.playMusic("main.wav");

    }

    private void initializeUI() {
        BoggledContainer timer = new HorizontalContainer();
        timer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        timer.addUIComponent(new BoggledWaitingTimer());

        BoggledContainer label = new VerticalContainer();
        label.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        label.addUIComponent(new BoggledText("WAITING...", 29));
        label.setPadding(new Spacing(100,0,0,0));


        BoggledContainer text = new VerticalContainer();
        text.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        text.setBackgroundColor(BoggledColors.MENU_BACKGROUND_COLOR);
        text.addUIComponent(new BoggledWaitingInfo());
        text.setPadding(new Spacing(200,70));

        boggledCanvas.addUIComponent(text);
        boggledCanvas.addUIComponent(timer);
        boggledCanvas.addUIComponent(label);

    }

    private void endWaiting() {
        boggledCanvas.clear();
        inputEnabled = false;
        BoggledContainer content = new VerticalContainer();
        content.addUIComponent(new BoggledHeader("NO PLAYERS JOINED", 72));
        gameMenu.setHeaderContent(content);
        cleanup();

        //audioPlayer.playMusic("SFX_UI_ROOM_ERROR.wav");
        toggleMenu(true);
    }

    public void toggleScore(boolean show) {
        if(show) {
            showScore = true;
            BoggledContainer content = new VerticalContainer();
            content.addUIComponent(new BoggledHeader("SCORE", 72));
            gameMenu.setHeaderContent(content);
            toggleMenu(true);

        } else {
            showScore = false;
            toggleMenu(false);

        }
    }

    private void toggleMenu(boolean shouldShowMenu) {
        if(shouldShowMenu && !boggledCanvas.hasComponent(gameMenu)) {
            boggledCanvas.addUIComponent(gameMenu);
        } else if (!shouldShowMenu) {
            boggledCanvas.removeComponent(gameMenu);
        }
    }

    @Override
    protected void handleInput() {
        if (inputEnabled && input.isPressed(KeyEvent.VK_ALT)) {
            System.out.println("Alt Pressed");
            toggleScore(!showScore);
        }
    }

    @Override
    public void update(Game game) throws SQLException {
        super.update(game);
        if(!showScore) {
            gameTimer.update();
            handleInput();
        } else {
            gameTimer.update();

        }
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

}
