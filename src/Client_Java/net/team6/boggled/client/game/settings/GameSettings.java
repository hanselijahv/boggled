package Client_Java.net.team6.boggled.client.game.settings;

import Client_Java.net.team6.boggled.common.core.Value;

public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;
    private final AudioSettings audioSettings;
    private final Value<Boolean> fullScreenMode;
    private Settings settings;

    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        audioSettings = new AudioSettings();
        fullScreenMode = new Value<>(false);
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public Value<Boolean> isFullScreenMode(){
        return fullScreenMode;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public void increaseGameSpeed() {
        gameSpeedMultiplier += 0.25;
    }

    public void decreaseGameSpeed() {
        gameSpeedMultiplier -= 0.25;
    }

    public double getGameSpeedMultiplier() {
        return gameSpeedMultiplier;
    }

    public AudioSettings getAudioSettings() {
        return audioSettings;
    }

    public Settings getGameSettings() {
        return settings;
    }
}
