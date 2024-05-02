package net.team6.boggled.client.state;

import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.time.Time;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.common.input.KeyInputConsumer;
import net.team6.boggled.common.input.MouseHandler;
import net.team6.boggled.common.gui.container.AlignableContainer;
import net.team6.boggled.common.gui.component.UICanvas;
import net.team6.boggled.server.Server;
import net.team6.boggled.server.settings.BoggledSettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class State {
    protected Input input;
    protected Time time;
    private State nextState;
    protected AudioPlayer audioPlayer;
    protected UICanvas uiCanvas;
    protected GameSettings gameSettings;
    protected BoggledSettings boggledSettings;
    protected Size windowSize;
    protected MouseHandler mouseHandler;
    protected KeyInputConsumer keyInputConsumer;


    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.windowSize = windowSize;
        this.input = input;
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        mouseHandler = new MouseHandler();
        uiCanvas = new UICanvas(windowSize);
        time = new Time();
    }

    public State(Size windowSize, Input input, BoggledSettings boggledSettings) {
        this.boggledSettings = boggledSettings;
        this.windowSize = windowSize;
        this.input = input;
        // audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        mouseHandler = new MouseHandler();
        uiCanvas = new UICanvas(windowSize);
        time = new Time();
    }

    public void update(Game game) throws SQLException {
        audioPlayer.update();
        time.update();
        uiCanvas.update(this);
        handleKeyInput();
        mouseHandler.update(this);

        if (nextState != null) {
            game.enterState(nextState);
        }
    }

    public void serverUpdate(Server server) throws SQLException {
        // audioPlayer.update();
        time.update();
        uiCanvas.update(this);
        mouseHandler.update(this);

        if (nextState != null) {
            server.enterState(nextState);
        }
    }

    private void handleKeyInput() {
        if (keyInputConsumer != null) {
            List<Integer> typedKeyBufferCopy = new ArrayList<>(input.getTypedKeyBuffer());

            for (int keyCode : typedKeyBufferCopy) {
                keyInputConsumer.onKeyPressed(keyCode);
            }
        } else {
            handleInput();
        }
    }


    public Time getTime() {
        return time;
    }

    public Input getInput() {
        return input;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public AlignableContainer getUiCanvas() {
        return uiCanvas;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public BoggledSettings getBoggledSettings() {
        return boggledSettings;
    }

    public Size getWindowSize() {
        return windowSize;
    }

    public void cleanup() {
        audioPlayer.clear();

    }

    public void resize(Size size) {
        windowSize = size;
        uiCanvas.resize(size);
    }

    protected abstract void handleInput();

    public KeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(KeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

}
