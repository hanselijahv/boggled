package com.wordgame.server.state.settings;

import com.wordgame.client.core.Value;

import com.wordgame.common.model.Settings;

public class BoggledSettings {
    private boolean debugMode;
    private double gameSpeedMultiplier;
    private Settings settings;
    private final Value<Boolean> fullScreenMode;

    public BoggledSettings(boolean debugMode) {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        //SettingsDB settingsDB = new SettingsDB();
        //settings = settingsDB.getSettings();
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

    public Settings getGameSettings() {
        return settings;
    }

}
