package accessdata;

import references.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class GameDB {

    public static String addGame(String playerId, int highestScore) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "INSERT INTO game (game_id, player_id, highest_score) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, generateGameID());
            preparedStatement.setString(2, playerId);
            preparedStatement.setInt(3, highestScore);
            preparedStatement.executeUpdate();
            System.out.println("[SUCCESS] Success adding game.");
            return "[SUCCESS] Success adding game.";
        } catch (SQLException e) {
            e.getMessage();
            System.out.println("[ERROR] Failed to add game.");
            return "[ERROR] Failed to add game.";
        }
    }


    public static ArrayList<Game> readGame() {
        try {
            Connection connection = DatabaseConnector.getInstance().getConnection();
            ArrayList<Game> gameArrayList = new ArrayList<>();
            String query = "SELECT * FROM game";
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Game game = new Game(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                gameArrayList.add(game);
            }
            return gameArrayList;
        } catch (SQLException e) {
            // TODO
            System.out.println("[ERROR] Error selecting admin accounts.");
            throw new RuntimeException(e);
        }
    }

    public static String updateGame(Game newGame, String oldGameId) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "UPDATE game SET game_id = ?, player_id = ?, highest_score = ? WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newGame.getGameId());
            preparedStatement.setString(2, newGame.getPlayerId());
            preparedStatement.setInt(3, newGame.getHighestScore());
            preparedStatement.setString(4, oldGameId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // TODO
                System.out.println("[SUCCESS] Game updated.");
                return "[SUCCESS] Game updated.";
            } else {
                // TODO
                System.out.println("[ERROR] Game not found or no changes made.");
                return "[ERROR] Game not found or no changes made.";
            }
        } catch (SQLException e) {
            // TODO
            System.out.println("[ERROR] Failed to update game: " + e.getMessage());
            return "[ERROR] Failed to update game";
        }
    }

    public static String deleteGame(String gameId) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "DELETE FROM game WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameId);
            preparedStatement.executeUpdate();
            // TODO
            System.out.println("[SUCCESS] Game deleted.");
            return "[SUCCESS] Game deleted.";
        } catch (SQLException e) {
            // TODO
            System.out.println("[ERROR] Game deletion failed.");
            return "[ERROR] Game deletion failed.";
        }
    }

    public static String generateGameID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replaceAll("-", "").substring(0, 10);

        if (GameDB.idExists(id)) {
            return generateGameID();
        } else {
            return id;
        }
    }

    public static boolean idExists(String id) {
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
