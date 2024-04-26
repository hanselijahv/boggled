package com.wordgame.server.state.settings;

import com.wordgame.client.core.Value;

import com.wordgame.common.model.Settings;
import com.wordgame.common.db.SettingsDAO;

import java.sql.SQLException;

public class BoggledSettings {
    private boolean debugMode;
    private double gameSpeedMultiplier;
    private Settings settings;
    private final Value<Boolean> fullScreenMode;

    public BoggledSettings(boolean debugMode) throws SQLException {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        SettingsDAO settingsDAO = new SettingsDAO();
        settings = settingsDAO.getAll().get(0);
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
