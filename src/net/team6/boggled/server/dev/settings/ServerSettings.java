package net.team6.boggled.server.dev.settings;

import net.team6.boggled.common.core.Value;

import net.team6.boggled.common.model.Settings;
import net.team6.boggled.common.db.SettingsDAO;

import java.sql.SQLException;

public class ServerSettings {
    private final double gameSpeedMultiplier;
    private final Settings settings;
    private final Value<Boolean> fullScreenMode;
    private final ServerAudioSettings audioSettings;

    public ServerSettings(boolean debugMode) throws SQLException {
        gameSpeedMultiplier = 1;
        audioSettings = new ServerAudioSettings();
        SettingsDAO settingsDAO = new SettingsDAO();
        settings = settingsDAO.getAll().get(0);
        fullScreenMode = new Value<>(false);
    }


    public Value<Boolean> isFullScreenMode() {
        return fullScreenMode;
    }
    public ServerAudioSettings getAudioSettings() {
        return audioSettings;
    }

    public double getGameSpeedMultiplier() {
        return gameSpeedMultiplier;
    }

    public Settings getGameSettings() {
        return settings;
    }

}
