package net.team6.boggled.common.db;

import net.team6.boggled.common.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private final Connection connection;
    public static PlayerDAO playerDAOImpl = new PlayerDAO();

    public PlayerDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    public boolean insert(Player player) throws SQLException {
        String sql = "INSERT INTO players (player_id, username, password, highest_score) VALUES (?, ?, ?, ?)";
        if (usernameExists(player.getUsername())) {
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, generatePlayerId());
            statement.setString(2, player.getUsername());
            statement.setString(3, player.getPassword());
            statement.setInt(4, 0);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(Player player) throws SQLException {
        String sql = "DELETE FROM players WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getPlayerId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Player player, String[] params) throws SQLException {
        String sql = "UPDATE players SET username = ?, password = ? WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, player.getPlayerId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateHighestScore(String username, int score) throws SQLException {
        String sql = "SELECT highest_score FROM players WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int currentHighScore = resultSet.getInt(1);
                if (score > currentHighScore) {
                    sql = "UPDATE players SET highest_score = ? WHERE username = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                        updateStatement.setInt(1, score);
                        updateStatement.setString(2, username);
                        return updateStatement.executeUpdate() > 0;
                    }
                }
            }
        }
        return false;
    }

    public List<Player> getTop5PlayersWithHighestScores() throws SQLException {
        List<Player> topPlayers = new ArrayList<>();

        String sql = "SELECT * FROM players ORDER BY highest_score DESC LIMIT 5";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String username = rs.getString("username");
            int highestScore = rs.getInt("highest_score");
            Player player = new Player(null, username, null);
            player.setHighestScore(highestScore);
            topPlayers.add(player);
        }

        return topPlayers;
    }

    public List<Player> getAll() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";
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

    public boolean authenticatePlayer(Player player) throws SQLException {
        List<Player> playerToAuth = getAll();
        String playerId = null;

        for (Player account1 : playerToAuth) {
            if (account1.getUsername().equals(player.getUsername()) && account1.getPassword().equals(player.getPassword())) {
                playerId = account1.getPlayerId();
            }
        }

        String sql2 = "SELECT COUNT(*) FROM players WHERE player_id = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql2)
        ) {
            preparedStatement.setString(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String generatePlayerId() throws SQLException {
        String sql = "SELECT player_id FROM players";
        int maxId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String idStr = resultSet.getString(1);
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        }
        return String.valueOf(maxId + 1);
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM players WHERE username = ?";
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
}