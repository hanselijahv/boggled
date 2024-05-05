package net.team6.boggled.common.db;

import net.team6.boggled.common.model.GamePlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GamePlayerDAO implements DAO<GamePlayer>{

	private final Connection connection;

	public GamePlayerDAO(){
		this.connection = DatabaseConnector.getInstance().getConnection();
	}

	@Override
	public List<GamePlayer> getAll() throws SQLException {
		List<GamePlayer> players = new ArrayList<>();
		String query = "SELECT * FROM game_players";

		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				GamePlayer gamePlayer = new GamePlayer(
					    resultSet.getString(1),
					    resultSet.getString(2),
					    resultSet.getBoolean(3)
				);
				players.add(gamePlayer);
			}

		} catch (SQLException e) {
			throw new SQLException("[ERROR] Failed to get all players: " + e.getMessage(), e);
		}

		return players;
	}

	@Override
	public boolean insert(GamePlayer gamePlayer) throws SQLException {
		String query = "INSERT INTO game_players (game_id, player_id, player_connected) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, gamePlayer.getGameID());
			preparedStatement.setString(2, gamePlayer.getPlayerID());
			preparedStatement.setBoolean(3, gamePlayer.isPlayerConnected());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SQLException("[ERROR] Failed to add player: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean update(GamePlayer gamePlayer, String[] params) throws SQLException {
		String query = "UPDATE game_players SET player_connected = ? WHERE game_id = ? AND player_id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setBoolean(1, gamePlayer.isPlayerConnected());	// TODO: Check if this is correct
			preparedStatement.setString(2, gamePlayer.getGameID());
			preparedStatement.setString(3, gamePlayer.getPlayerID());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SQLException("[ERROR] Failed to update player: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean delete(GamePlayer gamePlayer) throws SQLException {
		String query = "DELETE FROM game_players WHERE game_id = ? AND player_id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, gamePlayer.getGameID());
			preparedStatement.setString(2, gamePlayer.getPlayerID());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SQLException("[ERROR] Failed to delete player: " + e.getMessage(), e);
		}
	}
}
