package net.team6.boggled.common.db;

import net.team6.boggled.common.model.GameRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRoomDAO implements DAO<GameRoom> {

    private final Connection connection;

    public GameRoomDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    @Override
    public List<GameRoom> getAll() throws SQLException {
        List<GameRoom> rooms = new ArrayList<>();
        String query = "SELECT * FROM game_rooms";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                GameRoom gameRoom = new GameRoom(
                        resultSet.getString(1),
                        resultSet.getBoolean(2)
                );
                rooms.add(gameRoom);
            }
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to get all rooms: " + e.getMessage(), e);
        }

        return rooms;
    }

    @Override
    public boolean insert(GameRoom gameRoom) throws SQLException {
        String query = "INSERT INTO game_rooms (game_id, game_started) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, gameRoom.getRoomId());
                preparedStatement.setBoolean(2, gameRoom.isGameStarted());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to add room: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(GameRoom gameRoom, String[] params) throws SQLException {
            String query = "UPDATE game_rooms SET game_started = ? WHERE game_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                  preparedStatement.setBoolean(1, gameRoom.isGameStarted());
                  preparedStatement.setString(2, gameRoom.getRoomId());

                  return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                  throw new SQLException("[ERROR] Failed to update room: " + e.getMessage(), e);
            }
    }

    @Override
    public boolean delete(GameRoom gameRoom) throws SQLException {
            String query = "DELETE FROM game_rooms WHERE game_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                  preparedStatement.setString(1, gameRoom.getRoomId());

                  return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                  throw new SQLException("[ERROR] Failed to delete room: " + e.getMessage(), e);
            }
    }
}
