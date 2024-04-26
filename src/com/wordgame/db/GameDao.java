package com.wordgame.db;

import com.wordgame.references.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a game data access object.
 */
public class GameDao implements Dao<Game>{
	private List<Game> games = new ArrayList<>();

	/**
	 * Returns a list of all games.
	 * @return a list of all games
	 */
	@Override
	public List<Game> getAll() {
		return games;
	}

	/**
	 * Inserts a game into the list of games.
	 * @param game the game to be added to the list
	 * @return true if the game was successfully added to the list, false otherwise
	 */
	@Override
	public boolean insert(Game game) {
		return games.add(game);
	}

	/**
	 * Updates a game in the list of games.
	 * @param game the game to be updated
	 * @param params the new parameters for the game
	 * @return true if the game was successfully updated, false otherwise
	 */
	@Override
	public boolean update(Game game, String[] params) {
		game.setGameId(params[0]);
		game.setPlayer(params[1]);
		game.setHighestScore(Integer.parseInt(params[2]));
		return true;
	}

	/**
	 * Deletes a game from the list of games.
	 * @param game the game to be deleted
	 * @return true if the game was successfully deleted, false otherwise
	 */
	@Override
	public boolean delete(Game game) {
		return false;
	}
}
