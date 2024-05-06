package net.team6.boggled.common.db;

import net.team6.boggled.common.model.GameWinner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Game class
 */
public class GameResultDAO implements DAO<GameWinner> {
    private final Connection connection;

    /**
     * Constructor for the GameResultDAO class
     */
    public GameResultDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Insert a game into the database
     * @param gameWinner The game to insert
     * @return True if the game was inserted, false otherwise
     * @throws SQLException If an error occurs while inserting the game
     */
    @Override
    public boolean insert(GameWinner gameWinner) throws SQLException {
        String query = "INSERT INTO game_results (game_id, player_id, winning_score) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameWinner.getGameId());
            preparedStatement.setString(2, gameWinner.getPlayerId());
            preparedStatement.setInt(3, gameWinner.getWinningScore());

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
    public List<GameWinner> getAll() throws SQLException {
        ArrayList<GameWinner> gameWinnerArrayList = new ArrayList<>();
        String query = "SELECT * FROM game_results";

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                GameWinner gameWinner = new GameWinner(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                gameWinnerArrayList.add(gameWinner);
            }

            return gameWinnerArrayList;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Error selecting admin accounts: " + e.getMessage());
        }
    }

    /**
     *  Update a game in the database
     * @param gameWinner The game to update
     * @param params The new values for the game
     * @return True if the game was updated, false otherwise
     * @throws SQLException If an error occurs while updating the game
     */
    @Override
    public boolean update(GameWinner gameWinner, String[] params) throws SQLException {
        String query = "UPDATE game_results SET game_id = ?, player_id = ?, winning_score = ? WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setInt(3, Integer.parseInt(params[2]));
            preparedStatement.setString(4, gameWinner.getGameId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to update game: " + e.getMessage());
        }
    }

    /**
     *  Delete a game from the database
     * @param gameWinner The game to delete
     * @return True if the game was deleted, false otherwise
     * @throws SQLException If an error occurs while deleting the game
     */
    @Override
    public boolean delete(GameWinner gameWinner) throws SQLException {
        String query = "DELETE FROM game_results WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameWinner.getGameId());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to delete game: " + e.getMessage());
        }
    }
}
