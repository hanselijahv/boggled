package accessdata;

import references.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDB {
    private Connection connection;

    public PlayerDB() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    // TODO: id generator, checks player table for the highest player id,
    // parameters should only accept username and password
    public void addPlayer(Player player) {
        String sql = "INSERT INTO player (player_id, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getPlayerId());
            statement.setString(2, player.getUsername());
            statement.setString(3, player.getPassword());
            // TODO: do not add duplicate usernames
        } catch (SQLException e) {
            System.out.println("Error inserting player: " + e.getMessage());
        }
    }

    public boolean playerExists(String playerId) {
        String sql = "SELECT COUNT(*) FROM player WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking player existence: " + e.getMessage());
        }
        return false;
    }

    public boolean authenticatePlayer(Player player) {
        String sql = "SELECT COUNT(*) FROM player WHERE player_id = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getPlayerId());
            statement.setString(2, player.getPassword());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating player: " + e.getMessage());
        }
        return false;
    }

    public void removePlayer(Player player) {
        String sql = "DELETE FROM player WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getPlayerId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: method to return a list of players, update player details,

}