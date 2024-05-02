package net.team6.boggled.common.db;

import net.team6.boggled.common.model.GameRoom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameRoomDAO implements DAO<GameRoom> {

    private final Connection connection;

    public GameRoomDAO() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    @Override
    public List<GameRoom> getAll() throws SQLException {
        String sql = "INSERT INTO gamerooms (room_id, )";
    }

    @Override
    public boolean insert(GameRoom gameRoom) throws SQLException {
        return false;
    }

    @Override
    public boolean update(GameRoom gameRoom, String[] params) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(GameRoom gameRoom) throws SQLException {
        return false;
    }
}
