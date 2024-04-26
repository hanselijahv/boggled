package com.wordgame.db;

import com.wordgame.references.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a player data access object.
 */
public class PlayerDao implements Dao<Player>{
	private List<Player> players = new ArrayList<>();

	/**
	 * Returns a list of all players.
	 * @return a list of all players
	 */
	@Override
	public List<Player> getAll() {
		return players;
	}

	/**
	 * Inserts a player into the list of players.
	 * @param player the player to be added to the list
	 * @return true if the player was successfully added to the list, false otherwise
	 */
	@Override
	public boolean insert(Player player) {
		return players.add(player);
	}

	/**
	 * Updates a player in the list of players.
	 * @param player the player to be updated
	 * @param params the new parameters for the player
	 * @return true if the player was successfully updated, false otherwise
	 */
	@Override
	public boolean update(Player player, String[] params) {
		player.setUsername(params[0]);
		player.setPassword(params[1]);
		return true;
	}

	/**
	 * Deletes a player from the list of players.
	 * @param player the player to be deleted
	 * @return true if the player was successfully deleted, false otherwise
	 */
	@Override
	public boolean delete(Player player) {
		return players.remove(player);
	}
}
