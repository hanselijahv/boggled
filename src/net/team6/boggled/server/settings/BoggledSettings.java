package net.team6.boggled.server.settings;

import net.team6.boggled.common.core.Value;

import net.team6.boggled.common.model.Settings;
import net.team6.boggled.common.db.SettingsDAO;

import java.sql.SQLException;

public class BoggledSettings {
    private boolean debugMode;
    private double gameSpeedMultiplier;
    private final Settings settings;
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
