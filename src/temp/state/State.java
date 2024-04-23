package temp.state;

import temp.audio.AudioPlayer;
import temp.core.Size;
import temp.game.Game;
import temp.game.Time;
import temp.game.settings.GameSettings;
import temp.input.Input;
import temp.input.MouseHandler;
import temp.ui.AlignableContainer;
import temp.ui.UICanvas;
import temp.ui.UIComponent;
import temp.ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class State {
    protected Input input;
    protected Time time;
    private State nextState;
    protected AudioPlayer audioPlayer;
    protected UICanvas uiCanvas;
    protected GameSettings gameSettings;
    protected Size windowSize;
    protected MouseHandler mouseHandler;


    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.windowSize = windowSize;
        this.input = input;
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        mouseHandler = new MouseHandler();
        uiCanvas = new UICanvas(windowSize);
        time = new Time();
    }

    public void update(Game game) {
        audioPlayer.update();
        time.update();
        uiCanvas.update(this);
        mouseHandler.update(this);


        if (nextState != null) {
            game.enterState(nextState);
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

    public Size getWindowSize() {
        return windowSize;
    }

    public void cleanup() {
        new Thread(() -> {
            audioPlayer.clear();
        }).start();
    }

    public void resize(Size size) {
        windowSize = size;
        uiCanvas.resize(size);
    }

    protected abstract void handleInput();

}
