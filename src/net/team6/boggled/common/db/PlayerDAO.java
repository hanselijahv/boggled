package net.team6.boggled.common.db;

import net.team6.boggled.common.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Player class
 */
public class PlayerDAO implements DAO<Account> {
    private final Connection connection;

    public PlayerDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Insert a player into the database
     * @param account The player to insert
     * @return True if the player was inserted, false otherwise
     * @throws SQLException If an error occurs while inserting the player
     */
    @Override
    public boolean insert(Account account) throws SQLException {
            String sql = "INSERT INTO players (player_id, username, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                  statement.setString(1, generatePlayerId());
                  statement.setString(2, account.getUsername());
                  statement.setString(3, account.getPassword());
                  return statement.executeUpdate() > 0;
            }
    }

    /**
     * Delete a player from the database
     * @param account The player to delete
     * @return True if the player was deleted, false otherwise
     * @throws SQLException If an error occurs while deleting the player
     */
    @Override
    public boolean delete(Account account) throws SQLException {
        String sql = "DELETE FROM players WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getPlayerId());
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all players from the database
     * @return A list of all players
     * @throws SQLException If an error occurs while selecting the players
     */
    @Override
    public List<Account> getAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM players";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Account account = new Account();
                account.setPlayerId(resultSet.getString("player_id"));
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    /**
     * Update a player in the database
     * @param account The player to update
     * @param params The new values for the player
     * @return True if the player was updated, false otherwise
     * @throws SQLException If an error occurs while updating the player
     */
    @Override
    public boolean update(Account account, String[] params) throws SQLException {
        String sql = "UPDATE players SET username = ?, password = ? WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, account.getPlayerId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean authenticatePlayer(Account account) {
        String sql = "SELECT COUNT(*) FROM players WHERE player_id = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getPlayerId());
            statement.setString(2, account.getPassword());
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

    // TODO: Implement UUID for generating ID
    private String generatePlayerId() throws SQLException {
        String sql = "SELECT MAX(player_id) FROM players";
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