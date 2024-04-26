package com.wordgame.db;

import com.wordgame.references.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a settings data access object.
 */
public class SettingsDao implements Dao<Settings> {
	private List<Settings> settings = new ArrayList<>();

	/**
	 * Returns a list of all settings.
	 * @return a list of all settings
	 */
	@Override
	public List<Settings> getAll() {
		return settings;
	}

	/**
	 * Inserts a setting into the list of settings.
	 * @param setting the setting to be added to the list
	 * @return true if the setting was successfully added to the list, false otherwise
	 */
	@Override
	public boolean insert(Settings setting) {
		return settings.add(setting);
	}

	/**
	 * Updates a setting in the list of settings.
	 * @param setting the setting to be updated
	 * @param params the new parameters for the setting
	 * @return true if the setting was successfully updated, false otherwise
	 */
	@Override
	public boolean update(Settings setting, String[] params) {
		setting.setWaitingTime(Integer.parseInt(params[0]));
		setting.setRoundTime(Integer.parseInt(params[1]));
		setting.setRoundsToWin(Integer.parseInt(params[2]));
		return true;
	}

	/**
	 * Deletes a setting from the list of settings.
	 * @param setting the setting to be deleted
	 * @return true if the setting was successfully deleted, false otherwise
	 */
	@Override
	public boolean delete(Settings setting) {
		return settings.remove(setting);
	}
}
