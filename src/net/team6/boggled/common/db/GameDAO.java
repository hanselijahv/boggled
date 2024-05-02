package net.team6.boggled.common.db;

import net.team6.boggled.common.model.GameResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Access Object for the Game class
 */
public class GameDAO implements DAO<GameResult> {
    private final Connection connection;

    /**
     * Constructor for the GameDAO class
     */
    public GameDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Insert a game into the database
     * @param gameResult The game to insert
     * @return True if the game was inserted, false otherwise
     * @throws SQLException If an error occurs while inserting the game
     */
    @Override
    public boolean insert(GameResult gameResult) throws SQLException {
        String query = "INSERT INTO game (game_id, player_id, highest_score) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, generateGameID());
            preparedStatement.setString(2, gameResult.getPlayerId());
            preparedStatement.setInt(3, gameResult.getHighestScore());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to add game: " + e.getMessage(), e);
        }
    }

    /**
     * Get all games from the database
     * @return A list of all games
     * @throws SQLException If an error occurs while selecting the games
     */
    @Override
    public List<GameResult> getAll() throws SQLException {
        ArrayList<GameResult> gameResultArrayList = new ArrayList<>();
        String query = "SELECT * FROM game";

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                GameResult gameResult = new GameResult(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                gameResultArrayList.add(gameResult);
            }

            return gameResultArrayList;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Error selecting admin accounts: " + e.getMessage());
        }
    }

    /**
     *  Update a game in the database
     * @param gameResult The game to update
     * @param params The new values for the game
     * @return True if the game was updated, false otherwise
     * @throws SQLException If an error occurs while updating the game
     */
    @Override
    public boolean update(GameResult gameResult, String[] params) throws SQLException {
        String query = "UPDATE game SET game_id = ?, player_id = ?, highest_score = ? WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setInt(3, Integer.parseInt(params[2]));
            preparedStatement.setString(4, gameResult.getGameId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to update game: " + e.getMessage());
        }
    }

    /**
     *  Delete a game from the database
     * @param gameResult The game to delete
     * @return True if the game was deleted, false otherwise
     * @throws SQLException If an error occurs while deleting the game
     */
    @Override
    public boolean delete(GameResult gameResult) throws SQLException {
        String query = "DELETE FROM game WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameResult.getGameId());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to delete game: " + e.getMessage());
        }
    }

    /**
     * Generate a unique game ID
     * @return The generated game ID
     */
    private String generateGameID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replaceAll("-", "").substring(0, 10);

        if (idExists(id)) {
            return generateGameID();
        } else {
            return id;
        }
    }

    /**
     * Check if the generated ID already exists in the database
     * @param id The generated ID
     * @return True if the ID exists, false otherwise
     */
    private boolean idExists(String id) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM game WHERE game_id = ?";

        try {
            PreparedStatement statementGame = connection.prepareStatement(query);
            statementGame.setString(1, id);
            ResultSet resultSetGame = statementGame.executeQuery();
            if (resultSetGame.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
