package db;

import references.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDB {
    private Connection connection;

    public PlayerDB() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    public void addPlayer(String username, String password) throws SQLException {
        if (usernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        String playerId = generatePlayerId();
        String sql = "INSERT INTO player (player_id, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerId);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
        }
    }

    private String generatePlayerId() throws SQLException {
        String sql = "SELECT MAX(player_id) FROM player";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int maxId = resultSet.getInt(1);
                return String.valueOf(maxId + 1);
            }
        }
        return "1";
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM player WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM player";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Player player = new Player();
                player.setPlayerId(resultSet.getString("player_id"));
                player.setUsername(resultSet.getString("username"));
                player.setPassword(resultSet.getString("password"));
                players.add(player);
            }
        }
        return players;
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

    public void updatePlayer(Player player) throws SQLException {
        String sql = "UPDATE player SET username = ?, password = ? WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getUsername());
            statement.setString(2, player.getPassword());
            statement.setString(3, player.getPlayerId());
            statement.executeUpdate();
        }
    }

}