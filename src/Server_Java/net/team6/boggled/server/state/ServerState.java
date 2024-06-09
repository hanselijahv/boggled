package Server_Java.net.team6.boggled.server.state;


import Client_Java.net.team6.boggled.client.game.time.Time;
import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.audio.ServerAudioPlayer;
import Server_Java.net.team6.boggled.server.dev.Server;
import Server_Java.net.team6.boggled.server.dev.settings.ServerSettings;
import Server_Java.net.team6.boggled.server.gui.component.ServerCanvas;
import Server_Java.net.team6.boggled.server.gui.container.ServerAlignableContainer;
import Server_Java.net.team6.boggled.server.input.ServerInput;
import Server_Java.net.team6.boggled.server.input.ServerKeyInputConsumer;
import Server_Java.net.team6.boggled.server.input.ServerMouseHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerState {
    protected ServerInput serverInput;
    protected Time time;
    private ServerState nextState;
    protected ServerAudioPlayer serverAudioPlayer;
    protected ServerCanvas serverCanvas;
    protected ServerSettings serverSettings;
    protected Size windowSize;
    protected ServerMouseHandler serverMouseHandler;
    protected ServerKeyInputConsumer keyInputConsumer;


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
        handleKeyInput();
        serverMouseHandler.update(this);

        if (nextState != null) {
            server.enterState(nextState);
        }
    }

    private void handleKeyInput() {
        if (keyInputConsumer != null) {
            List<Integer> typedKeyBufferCopy = new ArrayList<>(serverInput.getTypedKeyBuffer());
            List<Integer> releasedKeysBufferCopy = new ArrayList<>(serverInput.getReleasedKeysBuffer());

            for (int keyCode : typedKeyBufferCopy) {
                keyInputConsumer.onKeyPressed(keyCode);
            }

            for (int keyCode : releasedKeysBufferCopy) {
                keyInputConsumer.onKeyReleased(keyCode);
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

    public ServerKeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(ServerKeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

}
