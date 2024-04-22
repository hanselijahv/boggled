package temp.state;

import temp.audio.AudioPlayer;
import temp.core.Size;
import temp.game.Game;
import temp.game.Time;
import temp.game.settings.GameSettings;
import temp.input.Input;
import temp.ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class State {
    protected Input input;
    protected List<UIContainer> uiContainers;
    protected Time time;
    private State nextState;
    protected AudioPlayer audioPlayer;
    protected GameSettings gameSettings;
    protected Size windowSize;


    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.windowSize = windowSize;
        this.input = input;
        time = new Time();
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        uiContainers = new ArrayList<>();
    }

    public void update(Game game) {
        time.update();
        for (UIContainer uiContainer : uiContainers) {
            uiContainer.update(this);

        }
        handleMouseInput();

        if (nextState != null) {
            game.enterState(nextState);
        }
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public Time getTime() {
        return time;
    }

    public Input getInput() {
        return input;
    }



    private void handleMouseInput() {
        input.clearMouseClick();
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
}
