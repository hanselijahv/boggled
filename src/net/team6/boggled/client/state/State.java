package net.team6.boggled.client.state;

import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.time.Time;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.input.KeyInputConsumer;
import net.team6.boggled.client.input.MouseHandler;
import net.team6.boggled.client.gui.container.AlignableContainer;
import net.team6.boggled.client.gui.component.BoggledCanvas;
import net.team6.boggled.server.dev.settings.ServerSettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class State {
    protected Input input;
    protected Time time;
    private State nextState;
    protected AudioPlayer audioPlayer;
    protected BoggledCanvas boggledCanvas;
    protected GameSettings gameSettings;
    protected ServerSettings serverSettings;
    protected Size windowSize;
    protected MouseHandler mouseHandler;
    protected KeyInputConsumer keyInputConsumer;


    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.windowSize = windowSize;
        this.input = input;
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        mouseHandler = new MouseHandler();
        boggledCanvas = new BoggledCanvas(windowSize);
        time = new Time();
    }


    public void update(Game game) throws SQLException {
        audioPlayer.update();
        time.update();
        boggledCanvas.update(this);
        handleKeyInput();
        mouseHandler.update(this);

        if (nextState != null) {
            game.enterState(nextState);
        }
    }


    private void handleKeyInput() {
        if (keyInputConsumer != null) {
            List<Integer> typedKeyBufferCopy = new ArrayList<>(input.getTypedKeyBuffer());
            List<Integer> releasedKeyBufferCopy = new ArrayList<>(input.getReleasedKeysBuffer());

            for (int keyCode : typedKeyBufferCopy) {
                keyInputConsumer.onKeyPressed(keyCode);
            }

            for (int keyCode : releasedKeyBufferCopy) {
                keyInputConsumer.onKeyReleased(keyCode);
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
        return boggledCanvas;
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


    public Size getWindowSize() {
        return windowSize;
    }

    public void cleanup() {
        audioPlayer.clear();

    }

    public void resize(Size size) {
        windowSize = size;
        boggledCanvas.resize(size);
    }

    protected abstract void handleInput();

    public KeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(KeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

}
