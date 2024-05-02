package net.team6.boggled.user.server.state;


import net.team6.boggled.user.client.game.settings.GameSettings;
import net.team6.boggled.user.client.game.time.Time;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.input.ServerInput;
import net.team6.boggled.user.client.input.KeyInputConsumer;
import net.team6.boggled.user.server.gui.container.ServerAlignableContainer;
import net.team6.boggled.user.server.input.ServerMouseHandler;
import net.team6.boggled.user.server.dev.Server;
import net.team6.boggled.user.server.audio.ServerAudioPlayer;
import net.team6.boggled.user.server.gui.component.ServerCanvas;
import net.team6.boggled.user.server.dev.settings.ServerSettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerState {
    protected ServerInput serverInput;
    protected Time time;
    private ServerState nextState;
    protected ServerAudioPlayer serverAudioPlayer;
    protected ServerCanvas serverCanvas;
    protected GameSettings gameSettings;
    protected ServerSettings serverSettings;
    protected Size windowSize;
    protected ServerMouseHandler serverMouseHandler;
    protected KeyInputConsumer keyInputConsumer;


    public ServerState(Size windowSize, ServerInput serverInput, ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
        this.windowSize = windowSize;
        this.serverInput = serverInput;
        serverAudioPlayer = new ServerAudioPlayer(serverSettings.getAudioSettings());
        serverMouseHandler = new ServerMouseHandler();
        serverCanvas = new ServerCanvas(windowSize);
        time = new Time();
    }


    public void update(Server server) throws SQLException {
        serverAudioPlayer.update();
        time.update();
        serverCanvas.update(this);
        serverMouseHandler.update(this);

        if (nextState != null) {
            server.enterState(nextState);
        }
    }

    private void handleKeyInput() {
        if (keyInputConsumer != null) {
            List<Integer> typedKeyBufferCopy = new ArrayList<>(serverInput.getTypedKeyBuffer());

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

    public ServerInput getInput() {
        return serverInput;
    }

    public ServerMouseHandler getMouseHandler() {
        return serverMouseHandler;
    }

    public ServerAlignableContainer getUiCanvas() {
        return serverCanvas;
    }

    public void setNextState(ServerState nextState) {
        this.nextState = nextState;
    }

    public ServerAudioPlayer getAudioPlayer() {
        return serverAudioPlayer;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public ServerSettings getBoggledSettings() {
        return serverSettings;
    }

    public Size getWindowSize() {
        return windowSize;
    }

    public void cleanup() {
        serverAudioPlayer.clear();

    }

    public void resize(Size size) {
        windowSize = size;
        serverCanvas.resize(size);
    }

    protected abstract void handleInput();

    public KeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(KeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

}
