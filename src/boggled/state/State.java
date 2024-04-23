package boggled.state;

import boggled.audio.AudioPlayer;
import boggled.core.Size;
import boggled.game.Game;
import boggled.game.time.Time;
import boggled.game.settings.GameSettings;
import boggled.input.Input;
import boggled.input.MouseHandler;
import boggled.ui.AlignableContainer;
import boggled.ui.UICanvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            audioPlayer.clear();
            executor.shutdown(); // Shut down the executor after the cleanup is complete
        });
    }

    public void resize(Size size) {
        windowSize = size;
        uiCanvas.resize(size);
    }

    protected abstract void handleInput();

}
